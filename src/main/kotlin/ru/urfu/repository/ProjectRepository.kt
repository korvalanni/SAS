package ru.urfu.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.urfu.models.Project
import java.util.*

@Repository
interface ProjectRepository: JpaRepository<Project, UUID> {

    fun findByTitle(title: String): List<Project>


}