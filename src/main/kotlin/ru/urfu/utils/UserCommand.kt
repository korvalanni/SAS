package ru.urfu.utils

sealed class UserCommand {
    data object FindAll : UserCommand()

}