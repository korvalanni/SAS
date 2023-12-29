package ru.urfu.dto.request.user.crud

import java.util.UUID

data class UserCreateRequestDTO (
    val name: String,
    val password: String,
    val projectId: UUID
){
    init {
//        require(name.isNotEmpty()) { "Name shouldn't be empty" }
//        require(name.count { it.isLetter() } >= 4) {
//            "Name should contain at least 4 letters"
//        }
//
//        require(password.isNotBlank()) { "Password shouldn't be blank" }
//        require(password.isNotEmpty()) { "Password shouldn't be empty" }
//        require(password.any { it.isDigit() }) { "Password should contain at least one digit" }
//        require(password.count { it.isLetter() } >= 4) {
//            "Password should contain at least 4 letters"
//        }
//        require(password.any { !it.isLetterOrDigit() }) {
//            "Password should contain at least 1 symbol"
//        }
    }
}