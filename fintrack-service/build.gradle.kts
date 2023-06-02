
val ktorVersion: String by project


plugins {

    kotlin("multiplatform")
}

repositories {
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
}
val koinVersion: String by project

kotlin {
    jvm {}
    macosX64 {}
    linuxX64 {}

    sourceSets {
        @Suppress("UNUSED_VARIABLE")
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation(project(":fintrack-common"))
                implementation(project(":fintrack-stubs"))

            }

        }
        @Suppress("UNUSED_VARIABLE")
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

    }
}
