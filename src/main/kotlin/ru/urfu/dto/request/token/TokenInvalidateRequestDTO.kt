package ru.urfu.dto.request.token

import ru.urfu.dto.request.PatchType
import java.util.UUID

sealed class TokenInvalidateRequestDTO(
    val type: PatchType
) {
    data class AccessTokenInvalidateDTO(
        val userId: UUID,
        val accessToken: String
    ) : TokenUpdateRequestDTO(
        type = PatchType.AccessToken
    )

    data class RefreshTokenInvalidateDTO(
        val userID: UUID,
        val refreshToken: String
    ) : TokenUpdateRequestDTO(
        type = PatchType.RefreshToken
    )

    data class TokenInvalidateDTO(
        val userId: UUID,
        val accessToken: String,
        val refreshToken: String
    ) : TokenUpdateRequestDTO(
        type = PatchType.Tokens
    )
}
