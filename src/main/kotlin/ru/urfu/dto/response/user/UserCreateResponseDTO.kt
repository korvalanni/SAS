package ru.urfu.dto.response.user

import java.time.LocalDateTime
import java.util.*

data class UserCreateResponseDTO (
    val id: UUID,
    val userName: String,
    val createdAt: LocalDateTime
)