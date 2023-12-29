package ru.urfu.dto.request.user.crud

import java.util.UUID

data class UserEntityRequestDTO (
    val id: UUID,
    val userName: String,
    val projectId: UUID
)