package ru.urfu.models.auth

import ru.urfu.models.Token

sealed class LoginResult {
    data class LoginSuccess(
        val redirectUrl: String,
        val token: Token
    ) : LoginResult()

    data object LoginFailed : LoginResult()
}