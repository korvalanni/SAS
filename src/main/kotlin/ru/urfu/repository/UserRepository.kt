package ru.urfu.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.urfu.models.UserEntity
import java.util.UUID

@Repository
interface UserRepository: JpaRepository<UserEntity, UUID> {

    fun findByUsernameAndProjectId(userName: String, projectId: UUID) : UserEntity?

    fun findByUsername(userName: String) : UserEntity?
}