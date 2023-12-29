package ru.urfu.services.auth

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.urfu.dto.request.user.auth.UserRequestDTO
import ru.urfu.models.auth.Code
import ru.urfu.models.auth.LoginResult
import ru.urfu.models.auth.RegistrationResult
import ru.urfu.repository.ProjectRepository
import ru.urfu.repository.UserRepository
import ru.urfu.services.project.ProjectService
import ru.urfu.services.token.TokenService
import ru.urfu.utils.toUserEntity

@Service
class AuthServiceImpl(
    private val userRepository: UserRepository,
    private val hashService: HashService,
    private val tokenService: TokenService,
    private val projectService: ProjectService,
    private val projectRepository: ProjectRepository
) : AuthService {

    companion object {
        private val logger = LoggerFactory.getLogger(AuthServiceImpl::class.java)
    }

    override suspend fun login(userDTO: UserRequestDTO): LoginResult {
        val user = userRepository.findByUsername(userDTO.login)
        if (user == null) {
            logger.error("Login failed: User not found with username: ${userDTO.login}")
            return LoginResult.LoginFailed
        }

        if (userDTO.projectId != user.project.id) {
            logger.error("Login failed: User not exist in this project: ${userDTO.projectId}")
            return LoginResult.LoginFailed
        }
//
//        try {
//            val project = projectService.read(user.project.id)
//            if (user.id !in project.userIds) {
//                logger.error("Login failed: User ${user.username} not part of project ${userDTO.projectId}")
//                return LoginResult.LoginFailed
//            }
//        } catch (e: Exception) {
//            logger.error("Login failed: Error reading project with ID: ${userDTO.projectId}", e)
//            return LoginResult.LoginFailed
//        }

        val hashPassword = hashService.calculateHash(userDTO.password)
        if (!hashService.validateHash(user.password, hashPassword)) {
            logger.error("Login failed: Password validation failed for user: ${user.username}")
            return LoginResult.LoginFailed
        }

        val token = tokenService.getOrCreate(user)
        logger.info("Login successful for user: ${user.username}")

        return LoginResult.LoginSuccess(
            redirectUrl = "http://app.korvalanni:8080/test",
            token = token
        )
    }

    override suspend fun registration(userDTO: UserRequestDTO): RegistrationResult {
        if (userRepository.findByUsername(userDTO.login) != null) {
            logger.error("Registration failed: User already exists with username: ${userDTO.login}")
            return RegistrationResult.RegistrationFailed(
                code = Code.INCORRECT_REQUEST,
                message = "User already exists"
            )
        }

        val projectOptional = projectRepository.findById(userDTO.projectId)
        if (!projectOptional.isPresent) {
            logger.error("Registration failed: Project not found with ID: ${userDTO.projectId}")
            return RegistrationResult.RegistrationFailed(
                code = Code.INCORRECT_REQUEST,
                message = "Project not found"
            )
        }

        val hashPassword = hashService.calculateHash(userDTO.password)
        val newUser = userDTO.toUserEntity(hashPassword, projectOptional.get())
        withContext(Dispatchers.IO) {
            userRepository.save(newUser)
        }
        logger.info("Registration successful for user: ${userDTO.login}")

        return RegistrationResult.RegistrationSuccess(userDTO.projectId)
    }

}
