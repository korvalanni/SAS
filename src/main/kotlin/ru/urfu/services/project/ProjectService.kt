package ru.urfu.services.project

import ru.urfu.dto.request.project.ProjectCreateRequestDTO
import ru.urfu.dto.response.project.ProjectInfoDTO
import java.util.*


interface ProjectService {
    suspend fun create(projectDTO: ProjectCreateRequestDTO): ProjectInfoDTO
    fun read(projectId: UUID): ProjectInfoDTO

    fun delete(projectId: UUID): ProjectInfoDTO


}