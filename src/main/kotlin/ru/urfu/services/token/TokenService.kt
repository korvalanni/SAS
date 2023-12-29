package ru.urfu.services.token

import ru.urfu.models.Token
import ru.urfu.models.UserEntity

interface TokenService {
    suspend fun getOrCreate(user: UserEntity): Token //искать в бд токен по userId и expiredDate
    // если токен не найден в базе то создается jwt token через json web token а потом запись в базу

    suspend fun validateToken(tokenValue: String): Boolean

    suspend fun regenerateToken(refreshToken: String): Token?
}