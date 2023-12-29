import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("org.springframework.boot") version "3.2.1"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "1.9.21"
    kotlin("plugin.spring") version "1.9.21"
    kotlin("plugin.jpa") version "1.9.21"
    id("com.google.cloud.tools.jib") version "2.3.0"
    application
}

application {
    mainClass.set("your.package.MainClass") // Replace with your main class
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "your.package.MainClass" // Replace with your main class
    }
}

allprojects {
    group = "com.yapp"
    version = "1.0.0"

    repositories {
        mavenCentral()
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "17"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
    maven { url = uri("https://repo.spring.io/snapshot") }
}

apply(plugin = "org.jetbrains.kotlin.jvm")

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web:3.2.1")
    implementation("org.springframework.boot:spring-boot-starter-validation:3.2.1")
    implementation("org.springframework.boot:spring-boot-starter-webflux:3.2.1")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.2.1")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")
    implementation("io.jsonwebtoken:jjwt-api:0.12.3") // Replace with the latest version
    implementation("io.jsonwebtoken:jjwt-impl:0.12.3") // Replace with the latest version
    implementation("io.jsonwebtoken:jjwt-jackson:0.12.3")

    implementation("org.springframework.boot:spring-boot-starter-thymeleaf:3.2.1")
    implementation("org.apache.logging.log4j:log4j-core:2.19.0")
    implementation("ch.qos.logback:logback-classic:1.4.12")
    implementation("org.postgresql:postgresql:42.7.1")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")


    runtimeOnly("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.7.3")
}

pluginManager.withPlugin("org.springframework.boot") {
    springBoot {
        buildInfo()
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}


tasks.getByName<BootJar>("bootJar") {
    isEnabled = false
}

tasks.getByName<Jar>("jar") {
    isEnabled = true
}


