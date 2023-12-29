package ru.urfu.services.user

import ru.urfu.dto.request.user.crud.UserCreateRequestDTO
import ru.urfu.dto.request.user.crud.UserUpdateRequestDTO
import ru.urfu.dto.response.user.UserCreateResponseDTO
import ru.urfu.dto.response.user.UserInfoDTO
import ru.urfu.dto.response.user.UserUpdateResponseDTO
import java.util.*

interface UserService {
    suspend fun create(userDTO: UserCreateRequestDTO): UserCreateResponseDTO
    suspend fun read(userName: String): UserInfoDTO

    suspend fun update(id: UUID, userUpdateRequestDTO: UserUpdateRequestDTO): UserUpdateResponseDTO

    suspend fun delete(userName: String):UserInfoDTO
}