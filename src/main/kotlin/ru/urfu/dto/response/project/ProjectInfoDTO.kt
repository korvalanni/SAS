package ru.urfu.dto.response.project


import java.util.UUID

data class ProjectInfoDTO(
    val id: UUID,
    val title:String,
    val userIds: List<UUID>
)