package ru.urfu.dto.request.project

data class ProjectCreateRequestDTO (
    val title: String,
    val redirectUrl: String
){
    init {
        require(title.count { it.isLetter() } >= 4) {
            "Title should contain at least 4 letters"
        }
    }
}