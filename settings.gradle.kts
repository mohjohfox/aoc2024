rootProject.name = "aoc2024"

pluginManagement {
    repositories {
        gradlePluginPortal()
    }
    val kotlinVersion: String by settings
    plugins {
        // Kotlin
        id("org.jetbrains.kotlin.jvm") version kotlinVersion
    }
}