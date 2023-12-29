package ru.urfu.controllers


import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import ru.urfu.dto.request.user.auth.UserRequestDTO
import ru.urfu.models.auth.LoginResult
import ru.urfu.models.auth.RegistrationResult
import ru.urfu.services.auth.AuthService
import ru.urfu.services.token.TokenService
import ru.urfu.utils.coroutineToMono


@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService,
    private val tokenService: TokenService
) {

    @PostMapping("/login")
    fun login(@RequestBody request: UserRequestDTO): Mono<*> {
        val result = coroutineToMono {
            when (val result = authService.login(request)) {
                is LoginResult.LoginFailed -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(mapOf(
                    "error" to "Can not be auth this user"
                ))
                is LoginResult.LoginSuccess -> {
                    ResponseEntity.status(HttpStatus.OK).body(mapOf(
                        "redirectUrl" to "${result.redirectUrl}?accessToken=${result.token.accessToken}&refreshToken=${result.token.refreshToken}"
                    ))
                }
            }
        }

        return result;
    }

    @PostMapping("/registration")
    fun registration(@RequestBody request: UserRequestDTO): Mono<*> {
        return coroutineToMono {
            when (val result = authService.registration(request)) {
                is RegistrationResult.RegistrationFailed -> return@coroutineToMono ResponseEntity.badRequest().body(result)
                is RegistrationResult.RegistrationSuccess -> let {
                    ResponseEntity.status(HttpStatus.OK).body(mapOf(
                        "redirectUrl" to "http://app.korvalanni:8080/login?appId=${result.appId}"
                    ))
                }
            }
        }
    }

    @PostMapping("/validate")
    fun validateToken(@RequestHeader authorization: String): Mono<*> {
        return coroutineToMono {
            val token = authorization.split("Bearer ")[1]
            if (!tokenService.validateToken(token)) {
                return@coroutineToMono ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(mapOf(
                    "error" to "Token invalid"
                ))
            } else {
                return@coroutineToMono ResponseEntity.ok().build()
            }
        }
    }

    @PostMapping("/tokens/refresh")
    fun regenerateToken(@RequestHeader refreshToken: String): ResponseEntity<out Any> {
        return coroutineToMono {
            val newToken = tokenService.regenerateToken(refreshToken)
            if (newToken == null) {
                return@coroutineToMono ResponseEntity.badRequest().body(mapOf(
                    "error" to "Token not valid. Need retry login"
                ))
            } else {
                return@coroutineToMono ResponseEntity.ok().body(
                    newToken
                )
            }
        }.block()!!
    }
}