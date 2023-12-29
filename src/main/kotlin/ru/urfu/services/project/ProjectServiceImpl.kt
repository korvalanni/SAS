package ru.urfu.services.project

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.urfu.dto.request.project.ProjectCreateRequestDTO
import ru.urfu.dto.response.project.ProjectInfoDTO
import ru.urfu.models.Project
import ru.urfu.repository.ProjectRepository
import ru.urfu.utils.projectToProjectInfoDTO
import java.time.LocalDateTime
import org.slf4j.LoggerFactory
import java.util.UUID

@Service
class ProjectServiceImpl(
    private val projectRepository: ProjectRepository
) : ProjectService {

    companion object {
        private val logger = LoggerFactory.getLogger(ProjectServiceImpl::class.java)
    }

    @Transactional
    override suspend fun create(projectDTO: ProjectCreateRequestDTO): ProjectInfoDTO {
        val project = Project(
            id = UUID.randomUUID(),
            title = projectDTO.title,
            users = emptyList(),
            createdAt = LocalDateTime.now(),
            redirectUrl = projectDTO.redirectUrl
        )
        withContext(Dispatchers.IO) {
            projectRepository.save(project)
        }
        logger.info("Created project with title: ${project.title} and ID: ${project.id}")
        return project.projectToProjectInfoDTO()
    }

    @Transactional(readOnly = true)
    override fun read(projectId: UUID): ProjectInfoDTO {
        val project = projectRepository.findById(projectId)
            .orElseThrow {
                logger.error("Project not found with ID: $projectId")
                IllegalArgumentException("Project not found with ID: $projectId")
            }
        logger.info("Read project with ID: $projectId")
        return project.projectToProjectInfoDTO()
    }

    @Transactional
    override fun delete(projectId: UUID): ProjectInfoDTO {
        val project = projectRepository.findById(projectId)
            .orElseThrow {
                logger.error("Project not found with ID: $projectId")
                IllegalArgumentException("Project not found with ID: $projectId")
            }
        projectRepository.delete(project)
        logger.info("Deleted project with ID: $projectId")
        return project.projectToProjectInfoDTO()
    }
}
