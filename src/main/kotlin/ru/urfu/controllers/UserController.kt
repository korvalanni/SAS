package ru.urfu.controllers

import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import ru.urfu.dto.request.user.crud.UserCreateRequestDTO
import ru.urfu.dto.request.user.crud.UserUpdateRequestDTO
import ru.urfu.dto.response.project.ProjectInfoDTO
import ru.urfu.dto.response.user.UserCreateResponseDTO
import ru.urfu.dto.response.user.UserInfoDTO
import ru.urfu.dto.response.user.UserUpdateResponseDTO
import ru.urfu.utils.coroutineToMono
import ru.urfu.services.user.UserService
import java.util.*


@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService
) {
    @PostMapping()
    fun create(@RequestBody userDTO: UserCreateRequestDTO): Mono<UserCreateResponseDTO> =
        coroutineToMono {
            userService.create(userDTO)
        }

    @PatchMapping("/{id}")
    fun update(@PathVariable id: UUID, @RequestBody userDTO: UserUpdateRequestDTO): Mono<UserUpdateResponseDTO> =
        coroutineToMono {
            userService.update(id = id, userUpdateRequestDTO = userDTO)
        }

    @GetMapping("/{name}")
    fun read(@PathVariable name: String): Mono<UserInfoDTO> =
        coroutineToMono {
            userService.read(name)
        }

    @DeleteMapping("/{name}")
    fun delete(@PathVariable  name: String): Mono<UserInfoDTO> =
        coroutineToMono {
            userService.delete(name)
        }


    @GetMapping("/ping")
    fun ping(): Mono<String> = coroutineToMono {
        "Пока сбер!!!"
    }
}