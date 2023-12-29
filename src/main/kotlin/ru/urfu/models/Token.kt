package ru.urfu.models

import jakarta.persistence.*
import java.time.Duration
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(
    name = "tokens"
)
data class Token(
    @Id
    val id: UUID,

    @Column(name = "user_id", nullable = false)
    val userId: UUID,

    @Column(name = "access_token", nullable = false, length = 512)
    val accessToken: String,

    @Column(name = "refresh_token", nullable = false, length = 512)
    val refreshToken: String,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "expired_date", nullable = false)
    val expiredDate: LocalDateTime,

    @Column(name = "used", nullable = false)
    val used: Boolean = false


) : EntityBase {
    init {
        // плохая валидация, зачем она?
//        require(accessToken.isBlank()) { "Access token shouldn`t be blank" }
//        require(refreshToken.isBlank()) { "Refresh token shouldn`t be blank" }
//        require(accessToken.isEmpty()) { "Access token shouldn`t be empty" }
//        require(refreshToken.isEmpty()) { "Refresh token shouldn`t be empty" }
    }

    override fun getNameEntity(): String {
        return Project::class.java.superclass.typeName
    }

    override fun getIdEntity(): String {
        return id.toString()
    }

}
