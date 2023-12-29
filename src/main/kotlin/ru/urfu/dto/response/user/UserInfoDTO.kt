package ru.urfu.dto.response.user

import ru.urfu.dto.response.project.ProjectInfoDTO
import java.util.UUID

data class UserInfoDTO (
    val id: UUID,
    val name: String,
    val projectInfo: ProjectInfoDTO
)