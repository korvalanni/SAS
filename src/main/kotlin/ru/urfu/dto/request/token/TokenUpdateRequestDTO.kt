package ru.urfu.dto.request.token

import ru.urfu.dto.request.PatchType
import java.util.*

sealed class TokenUpdateRequestDTO (
    val type: PatchType
){
    data class AccessTokenUpdateDTO(
        val userId: UUID,
        val refreshToken: String
    ): TokenUpdateRequestDTO(
        type = PatchType.AccessToken
    )

    data class BothTokenUpdateDTO(
        val userId: UUID
    ): TokenUpdateRequestDTO(
        type = PatchType.Tokens
    )

}