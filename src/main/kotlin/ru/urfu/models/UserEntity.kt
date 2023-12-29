package ru.urfu.models

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(
    name = "my_users",
    indexes = [
        Index(name = "uniqueIndex", columnList = "username, project_id", unique = true),
    ]
)
data class UserEntity(
    @Id
    val id: UUID,

    @Column(name = "username", nullable = false)
    val username: String,

    @Column(name = "password", nullable = false)
    val password: String,

    @ManyToOne
    @JoinColumn(name = "project_id")
    val project: Project,

    @Column(name = "createdAt", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()

) : EntityBase {
//    init {
//        require(username.isNotBlank()) { "Username shouldn't be blank" }
//        require(username.isNotEmpty()) { "Username shouldn't be empty" }
//        require(password.isNotEmpty()) { "Password shouldn't be empty" }
//        require(password.any { it.isDigit() }) { "Password should contain at least one digit" }
//        require(password.count { it.isLetter() } >= 4) { "Password should contain at least 4 letters" }
//        require(password.any { !it.isLetterOrDigit() }) { "Password should contain at least 1 symbol" }
//    }

    override fun getNameEntity(): String {
        return UserEntity::class.java.superclass.typeName
    }

    override fun getIdEntity(): String {
        return id.toString()
    }

}