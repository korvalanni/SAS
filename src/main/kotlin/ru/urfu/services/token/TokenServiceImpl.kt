package ru.urfu.services.token


import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.urfu.models.Token
import ru.urfu.models.UserEntity
import ru.urfu.repository.TokenRepository
import ru.urfu.repository.UserRepository
import java.time.LocalDateTime
import java.util.*


@Service
class TokenServiceImpl(
    private val tokenRepository: TokenRepository,
    private val userRepository: UserRepository
) : TokenService {

    private suspend fun generateToken(user: UserEntity, type: Int): String {
        val now = Date()
        val expiration = Date(now.time + 20_000)
        val builder = Jwts.builder()
            .claim("userId", user.id)
            .claim("username", user.username)
            .claim("projectId", user.project.id)
            .claim("tokenType", type)
            .signWith(SECRET_KEY_OBJ, SignatureAlgorithm.HS256)

        if (type == 0) {
            builder.expiration(expiration)
        }
        if (type == 1) {
            builder.claim("hash", UUID.randomUUID())
        }
        return builder.compact()
    }

    override suspend fun getOrCreate(user: UserEntity): Token {
        return tokenRepository.findByUserIdAndExpiredDateAfterAndUsed(
            user.id,
            LocalDateTime.now(),
            used = false
        ) ?: let {
            val accessToken = generateToken(user, 0)
            val refreshToken = generateToken(user, 1)
            Token(
                id = UUID.randomUUID(),
                userId = user.id,
                accessToken = accessToken,
                refreshToken = refreshToken,
                expiredDate = LocalDateTime.now().plusHours(30)
            ).also {
                tokenRepository.save(it)
                logger.info("Generated new tokens for user: ${user.username}")
            }
        }
    }

    override suspend fun validateToken(tokenValue: String): Boolean {
        val token = withContext(Dispatchers.IO) {
            tokenRepository.findByAccessTokenAndExpiredDateAfter(tokenValue, LocalDateTime.now())
        }
        val isValid = token != null
        logger.info("Token validation result for token $token: $isValid")
        return isValid
    }

    override suspend fun regenerateToken(refreshToken: String): Token? {
        val token = tokenRepository.findByRefreshTokenAndUsed(refreshToken, false)
        if(token != null){
            val user = userRepository.findById(token.userId).get()
            logger.info("Regenerating token for user: ${user.username}")
            tokenRepository.save(token.copy(
                used = true
            )) // TODO: надо нормальный патч с результатом
            return getOrCreate(user)
        } else {
            logger.error("Failed to regenerate token: Refresh token not found or already used")
            return null
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(TokenServiceImpl::class.java)
        private const val SECRET_KEY = "test_1234123-asdkajshdkasjhasdfasdf"
        private val SECRET_KEY_OBJ = Keys.hmacShaKeyFor(SECRET_KEY.toByteArray())
    }
}