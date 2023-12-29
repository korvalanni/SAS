package ru.urfu.dto.request.user.auth

import java.util.*

data class UserRequestDTO (
    val login : String,
    val password: String,
    val projectId: UUID
)