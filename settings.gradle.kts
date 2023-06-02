rootProject.name = "fintrack"

pluginManagement {
    val kotlinVersion: String by settings
    val kotestVersion: String by settings
    val openapiVersion: String by settings
    val ktorPluginVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion
        kotlin("multiplatform") version kotlinVersion apply false
        id("io.kotest.multiplatform") version kotestVersion apply false
        kotlin("plugin.serialization") version kotlinVersion apply false
        id("io.ktor.plugin") version ktorPluginVersion apply false
        id("org.openapi.generator") version openapiVersion apply false
    }
}


include("fintrack-common")
include("fintrack-api")
include("fintrack-mapper")

include("fintrack-stubs")
include("fintrack-ktor")
include("fintrack-service")
include("fintrack-ktor-plugin")

