import org.jetbrains.kotlin.gradle.dsl.JvmTarget

group = "de.mohjohfox"
version = "0.0.1"

plugins {
    java

    // Kotlin
    kotlin("jvm")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.commons:commons-collections4:4.4")
    implementation("org.apache.commons:commons-lang3:3.12.0")
}


tasks {
    val myJar by creating(Jar::class) {
        manifest {
            attributes["Main-Class"] = "de.mohjohfox.MainKt"
        }
        from(sourceSets.main.get().output)
        archiveFileName.set("mohjohfox-aoc2024.jar")
    }

    compileKotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
    }

    compileTestKotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

tasks.withType<Test> {
    useJUnitPlatform()
}