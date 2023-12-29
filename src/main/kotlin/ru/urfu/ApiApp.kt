package ru.urfu

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["ru.urfu"])
class ApiApp

fun main(args: Array<String>) {
    runApplication<ApiApp>(*args)
}