package ru.urfu.services.auth

interface HashService {
    suspend fun calculateHash(dataString: String): String

    suspend fun validateHash(expected: String, actual: String): Boolean

}