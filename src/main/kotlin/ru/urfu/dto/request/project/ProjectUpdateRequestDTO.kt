package ru.urfu.dto.request.project

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import ru.urfu.dto.request.PatchType
import ru.urfu.dto.request.user.crud.UserEntityRequestDTO
import java.util.UUID

@JsonTypeInfo(property = "type", use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY)
@JsonSubTypes(
    JsonSubTypes.Type(name = "AddUser", value = ProjectUpdateRequestDTO.AddUserToProjectDTO::class)
    )
sealed class ProjectUpdateRequestDTO(
    open val id: UUID,
    val type: PatchType
) {
     data class ProjectNameUpdateDTO(
       override val id: UUID,
        val updateName: String
    ) : ProjectUpdateRequestDTO(
        id = id,
        type = PatchType.Name
    ) {
        init {
            require(updateName.isNotEmpty()) { "UpdateName shouldn't be empty" }
            require(updateName.count { it.isLetter() } >= 4) {
                "UpdateName should contain at least 4 letters"
            }
        }
    }

    data class AddUserToProjectDTO(
        override val id: UUID,
        val user: UserEntityRequestDTO
    ) : ProjectUpdateRequestDTO(
        id = id,
        type = PatchType.AddUser
    )

    data class AddUsersToProjectDTO(
        override val id: UUID,
        val users: List<UserEntityRequestDTO>
    ) : ProjectUpdateRequestDTO(
        id = id,
        type = PatchType.AddUsers
    )

    data class UpdateNameAddUsersProjectDTO(
        override val id: UUID,
        val updateName: String,
        val users: List<UserEntityRequestDTO>
    ) : ProjectUpdateRequestDTO(
        id = id,
        type = PatchType.Project
    ) {
        init {
            require(updateName.isNotEmpty()) { "UpdateName shouldn't be empty" }
            require(updateName.count { it.isLetter() } >= 4) {
                "UpdateName should contain at least 4 letters"
            }

        }
    }

}

