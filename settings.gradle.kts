rootProject.name = "otusKotlin"

pluginManagement {
    val kotlinVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion
        kotlin("multiplatform") version kotlinVersion apply false
    }
}

include("m1l2")
