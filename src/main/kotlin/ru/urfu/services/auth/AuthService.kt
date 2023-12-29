package ru.urfu.services.auth

import ru.urfu.dto.request.user.auth.UserRequestDTO
import ru.urfu.models.auth.LoginResult
import ru.urfu.models.auth.RegistrationResult

interface AuthService {
    suspend fun login(userDTO: UserRequestDTO): LoginResult
    suspend fun registration(userDTO: UserRequestDTO): RegistrationResult
}