package ru.urfu.models.auth

import java.util.*

sealed class RegistrationResult {
    data class RegistrationSuccess(
        val appId: UUID
    ) : RegistrationResult()

    data class RegistrationFailed(
        val code: Code,
        val message: String
    ) : RegistrationResult()
}