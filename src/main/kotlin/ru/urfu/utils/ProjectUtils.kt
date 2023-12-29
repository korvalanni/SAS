package ru.urfu.utils

import ru.urfu.dto.response.project.ProjectInfoDTO
import ru.urfu.models.Project

fun Project.projectToProjectInfoDTO(): ProjectInfoDTO {
    return ProjectInfoDTO(
        id = this.id,
        title = this.title,
        userIds = this.users.map { it.id }
    )
}
