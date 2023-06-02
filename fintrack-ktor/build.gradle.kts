import org.jetbrains.kotlin.util.suffixIfNot


val ktorVersion: String by project
val logbackVersion: String by project
val serializationVersion: String by project
val koinVersion: String by project

plugins {
    id("application")
    kotlin("plugin.serialization")
    kotlin("multiplatform")
}

application {
    mainClass.set("ru.otus.otuskotlin.fintrack.app.ApplicationKt")
}

repositories {
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
}


fun ktor(
    module: String,
    prefix: String = "server-",
    version: String? = this@Build_gradle.ktorVersion
): Any =
    "io.ktor:ktor-${prefix.suffixIfNot("-")}$module:$version"

kotlin {
    jvm {}

    sourceSets {

        @Suppress("UNUSED_VARIABLE")
        val jvmMain by getting {
            dependencies {
                dependencies {
                    implementation(kotlin("stdlib-common"))
                    implementation(ktor("core")) // "io.ktor:ktor-server-core:$ktorVersion"
                    implementation(ktor("netty"))
                    implementation(ktor("caching-headers")) // "io.ktor:ktor-server-caching-headers:$ktorVersion"
                    implementation(ktor("cors")) // "io.ktor:ktor-server-cors:$ktorVersion"
                    implementation(ktor("content-negotiation"))
                    implementation("io.insert-koin:koin-core:$koinVersion")
                    implementation("io.insert-koin:koin-ktor:$koinVersion")// "io.ktor:ktor-server-content-negotiation:$ktorVersion"

                    implementation(project(":fintrack-common"))
                    implementation(project(":fintrack-api"))
                    implementation(project(":fintrack-mapper"))
                    implementation(project(":fintrack-stubs"))
                    implementation(project(":fintrack-service"))
                    implementation(project(":fintrack-ktor-plugin"))

                    implementation("ch.qos.logback:logback-classic:$logbackVersion")
                    implementation(ktor("call-logging"))

                    implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
                    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion") // "io.ktor:ktor-server-auth:$ktorVersion"
                }
            }
        }

        @Suppress("UNUSED_VARIABLE")
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation(ktor("test-host")) // "io.ktor:ktor-server-test-host:$ktorVersion"
                implementation(ktor("content-negotiation", prefix = "client-"))
            }
        }
    }
}
