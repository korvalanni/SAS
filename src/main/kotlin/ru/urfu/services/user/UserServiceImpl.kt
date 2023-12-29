package ru.urfu.services.user

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.urfu.dto.request.user.crud.UserCreateRequestDTO
import ru.urfu.dto.request.user.crud.UserUpdateRequestDTO
import ru.urfu.dto.response.user.UserCreateResponseDTO
import ru.urfu.dto.response.user.UserInfoDTO
import ru.urfu.dto.response.user.UserUpdateResponseDTO
import ru.urfu.models.UserEntity
import ru.urfu.repository.ProjectRepository
import ru.urfu.repository.UserRepository
import ru.urfu.utils.toUserCreateResponseDTO
import ru.urfu.utils.toUserInfoDTO
import ru.urfu.utils.toUserUpdateResponseDTO
import java.time.LocalDateTime

import org.slf4j.LoggerFactory
import ru.urfu.services.auth.HashService
import java.util.UUID


@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val projectRepository: ProjectRepository,
    private val hashService: HashService
) : UserService {

    companion object {
        private val logger = LoggerFactory.getLogger(UserServiceImpl::class.java)
    }

    @Transactional
    override suspend fun create(userDTO: UserCreateRequestDTO): UserCreateResponseDTO {
        userRepository.findByUsernameAndProjectId(userDTO.name, userDTO.projectId)?.let {
            val errorMessage = "User already exists with name: ${userDTO.name}"
            logger.error(errorMessage)
            throw IllegalArgumentException(errorMessage)
        }

        val project = projectRepository.findById(userDTO.projectId)
            .orElseThrow {
                val errorMessage = "Project not found with ID: ${userDTO.projectId}"
                logger.error(errorMessage)
                IllegalArgumentException(errorMessage)
            }

        val hashedPassword = hashService.calculateHash(userDTO.password)
        val newUser = UserEntity(
            id = UUID.randomUUID(),
            username = userDTO.name,
            password = hashedPassword,
            createdAt = LocalDateTime.now(),
            project = project
        )
        val savedUser = userRepository.save(newUser)
        logger.info("Created new user with id: ${savedUser.id}")
        return savedUser.toUserCreateResponseDTO()
    }

    @Transactional(readOnly = true)
    override suspend fun read(userName: String): UserInfoDTO {
        val user = userRepository.findByUsername(userName)
            ?: throw IllegalArgumentException("User not found with name: $userName").also {
                logger.error("Read operation failed: User not found with name: $userName")
            }

        logger.info("Read user with name: $userName")
        return user.toUserInfoDTO()
    }

    @Transactional
    override suspend fun update(id: UUID, userUpdateRequestDTO: UserUpdateRequestDTO): UserUpdateResponseDTO {
        return when (userUpdateRequestDTO) {
            is UserUpdateRequestDTO.UserNameUpdateDTO -> {
                val user = userRepository.findByUsername(userUpdateRequestDTO.currentName)
                    ?: throw IllegalArgumentException(
                        "User not found with name: ${userUpdateRequestDTO.currentName}"
                    )

                val updatedUser = user.copy(username = userUpdateRequestDTO.updateName)
                userRepository.save(updatedUser).also {
                    logger.info("Updated username for user with id: ${it.id}")
                }.toUserUpdateResponseDTO()
            }
            is UserUpdateRequestDTO.UserPasswordUpdateDTO -> {
                val user = userRepository.findByUsername(userUpdateRequestDTO.currentPassword)
                    ?: throw IllegalArgumentException(
                        "User not found with name: ${userUpdateRequestDTO.currentPassword}"
                    )

                val hashedPassword = hashService.calculateHash(userUpdateRequestDTO.updatedPassword)
                val updatedUser = user.copy(password = hashedPassword)
                userRepository.save(updatedUser).also {
                    logger.info("Updated password for user with id: ${it.id}")
                }.toUserUpdateResponseDTO()
            }
            is UserUpdateRequestDTO.UserNamePasswordUpdateDTO -> {
                val user = userRepository.findByUsername(userUpdateRequestDTO.currentName)
                    ?: throw IllegalArgumentException("User not found with name: ${userUpdateRequestDTO.currentName}")

                val hashedPassword = hashService.calculateHash(userUpdateRequestDTO.updatedPassword)
                val updatedUser = user.copy(
                    username = userUpdateRequestDTO.updateName,
                    password = hashedPassword
                )
                userRepository.save(updatedUser).also {
                    logger.info("Updated username and password for user with id: ${it.id}")
                }.toUserUpdateResponseDTO()
            }
        }
    }

    @Transactional
    override suspend fun delete(userName: String): UserInfoDTO {
        val user = userRepository.findByUsername(userName)
            ?: throw IllegalArgumentException("User not found with name: $userName").also {
                logger.error("Delete operation failed: User not found with name: $userName")
            }

        userRepository.delete(user)
        logger.info("Deleted user with name: $userName")
        return user.toUserInfoDTO()
    }
}