package ru.urfu.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.urfu.models.Token
import java.time.LocalDateTime
import java.util.*


interface TokenRepository: JpaRepository<Token, UUID> {
    fun findByUserIdAndExpiredDateAfterAndUsed(userId: UUID, currentDate: LocalDateTime, used: Boolean): Token?
    fun findByAccessTokenAndExpiredDateAfter(token: String, dateTime: LocalDateTime): Token?
    fun findByRefreshTokenAndUsed(token: String, used: Boolean): Token?
}