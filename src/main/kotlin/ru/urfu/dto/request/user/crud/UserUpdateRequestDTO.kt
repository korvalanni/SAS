package ru.urfu.dto.request.user.crud

import ru.urfu.dto.request.PatchType

sealed class UserUpdateRequestDTO(
    val type: PatchType
) {

    data class UserNameUpdateDTO(
        val currentName: String,
        val updateName: String
    ) : UserUpdateRequestDTO(type = PatchType.Name){
        init {
            require(updateName.isNotEmpty()) { "UpdateName shouldn't be empty" }
            require(updateName.count { it.isLetter() } >= 4) {
                "UpdateName should contain at least 4 letters"
            }
        }
    }

    data class UserPasswordUpdateDTO(
        val currentPassword: String,
        val updatedPassword: String
    ) : UserUpdateRequestDTO(type = PatchType.Password){
        init {
            require(updatedPassword.isNotBlank()) { "Password shouldn't be blank" }
            require(updatedPassword.isNotEmpty()) { "Password shouldn't be empty" }
            require(updatedPassword.any { it.isDigit() }) { "Password should contain at least one digit" }
            require(updatedPassword.count { it.isLetter() } >= 4) {
                "Password should contain at least 4 letters"
            }
            require(updatedPassword.any { !it.isLetterOrDigit() }) {
                "Password should contain at least 1 symbol"
            }
        }
    }

    data class UserNamePasswordUpdateDTO(
        val currentName: String,
        val updateName: String,
        val currentPassword: String,
        val updatedPassword: String
    ) : UserUpdateRequestDTO(type = PatchType.NamePassword){
        init {
            require(updateName.isNotEmpty()) { "UpdateName shouldn't be empty" }
            require(updateName.count { it.isLetter() } >= 4) {
                "UpdateName should contain at least 4 letters"
            }
            
            require(updatedPassword.isNotBlank()) { "Password shouldn't be blank" }
            require(updatedPassword.isNotEmpty()) { "Password shouldn't be empty" }
            require(updatedPassword.any { it.isDigit() }) { "Password should contain at least one digit" }
            require(updatedPassword.count { it.isLetter() } >= 4) {
                "Password should contain at least 4 letters"
            }
            require(updatedPassword.any { !it.isLetterOrDigit() }) {
                "Password should contain at least 1 symbol"
            }
        }
    }
}