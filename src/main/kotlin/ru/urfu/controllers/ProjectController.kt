package ru.urfu.controllers

import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import ru.urfu.dto.request.project.ProjectCreateRequestDTO
import ru.urfu.dto.request.project.ProjectUpdateRequestDTO

import ru.urfu.dto.response.project.ProjectInfoDTO
import ru.urfu.services.project.ProjectService
import ru.urfu.utils.coroutineToMono
import java.util.*

@RestController
@RequestMapping("/projects")
class ProjectController(
    private val projectService: ProjectService
) {
    @PostMapping
    fun create(@RequestBody projectDTO: ProjectCreateRequestDTO): Mono<ProjectInfoDTO> {
        return coroutineToMono {
            projectService.create(projectDTO)
        }
    }


    @GetMapping("/{id}")
    fun read(@PathVariable id: UUID): Mono<ProjectInfoDTO> =
        coroutineToMono {
            projectService.read(id)
        }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID): Mono<ProjectInfoDTO> =
        coroutineToMono {
            projectService.delete(id)
        }
}