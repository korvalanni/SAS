package ru.urfu.services.auth

import org.springframework.stereotype.Service
import java.security.MessageDigest

@Service
class HashServiceImpl :
    HashService {
    override suspend fun calculateHash(dataString: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(dataString.toByteArray(Charsets.UTF_8))
        return hashBytes.joinToString(separator = "") { "%02x".format(it) }
    }

    override suspend fun validateHash(expected: String, actual: String): Boolean {
        return expected == actual
    }
}