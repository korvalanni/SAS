package ru.urfu.dto.request

enum class PatchType {
    Name,
    Password,
    NamePassword,
    AccessToken,
    RefreshToken,
    Tokens,
    AddUser,
    AddUsers,
    Project,
    Unknown
}