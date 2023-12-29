package ru.urfu.utils

import ru.urfu.dto.request.user.auth.UserRequestDTO
import ru.urfu.dto.response.project.ProjectInfoDTO
import ru.urfu.dto.response.user.UserCreateResponseDTO
import ru.urfu.dto.response.user.UserInfoDTO
import ru.urfu.dto.response.user.UserUpdateResponseDTO
import ru.urfu.models.Project
import ru.urfu.models.UserEntity
import java.time.LocalDateTime
import java.util.*

fun UserEntity.toUserCreateResponseDTO() = UserCreateResponseDTO(
    id = this.id,
    userName = this.username,
    createdAt = this.createdAt
)

fun UserEntity.toUserInfoDTO() = UserInfoDTO(
    id = this.id,
    name = this.username,
    projectInfo = this.project.let { project ->
        ProjectInfoDTO(
            id = project.id,
            title = project.title,
            userIds = project.users.map { it.id }
        )
    }
)


fun UserEntity.toUserUpdateResponseDTO() = UserUpdateResponseDTO(
    id = this.id,
    userName = this.username,
    createdAt = this.createdAt,
    updatedAt = LocalDateTime.now()
)

fun UserRequestDTO.toUserEntity(hashedPassword: String, project: Project): UserEntity {
    return UserEntity(
        id = UUID.randomUUID(),
        username = this.login,
        password = hashedPassword,
        project = project,
        createdAt = LocalDateTime.now()
    )
}
