package ru.urfu.models

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*


@Entity
@Table(
    name = "projects"
)
data class Project(
    @Id
    @JsonProperty("project_id")
    val id: UUID,

    @Column(name = "title", nullable = false)
    val title: String,

    @OneToMany(mappedBy = "project")
    val users: List<UserEntity>,

    @Column(name = "createdAt", nullable = false)
    val createdAt: LocalDateTime,

    @Column(name = "updatedAt", nullable = false)
    val updatedAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "redirectUrl", nullable = false)
    val redirectUrl: String
) : EntityBase {

    override fun getNameEntity(): String {
        return Project::class.java.superclass.typeName
    }

    override fun getIdEntity(): String {
        return id.toString()
    }
}
