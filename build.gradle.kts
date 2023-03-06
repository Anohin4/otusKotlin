import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


plugins {
    kotlin("jvm")
    id ("io.kotest.multiplatform")
}

group = "ru.otus.otusKotlin"
version = "0.0.1-SNAPSHOT"

allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }

}

subprojects {
    group = rootProject.group
    version = rootProject.version

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "11"
    }

    tasks.withType<Test>().configureEach {
        useJUnitPlatform()
    }
}
dependencies {
    implementation(kotlin("stdlib"))
}
repositories {
    mavenCentral()
}
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}