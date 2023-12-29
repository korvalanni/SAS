package ru.urfu.models

import com.fasterxml.jackson.annotation.JsonIgnore

interface EntityBase {
    @JsonIgnore
    fun getNameEntity(): String

    @JsonIgnore
    fun getIdEntity(): String
}