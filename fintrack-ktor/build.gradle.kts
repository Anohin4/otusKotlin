import org.jetbrains.kotlin.util.suffixIfNot
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


val ktorVersion: String by project
val logbackVersion: String by project
val serializationVersion: String by project

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


fun ktor(module: String, prefix: String = "server-", version: String? = this@Build_gradle.ktorVersion): Any =
    "io.ktor:ktor-${prefix.suffixIfNot("-")}$module:$version"

kotlin {
    jvm {}

    sourceSets {
        @Suppress("UNUSED_VARIABLE")
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation(ktor("core")) // "io.ktor:ktor-server-core:$ktorVersion"
                implementation(ktor("netty"))
                implementation(ktor("caching-headers")) // "io.ktor:ktor-server-caching-headers:$ktorVersion"
                implementation(ktor("cors")) // "io.ktor:ktor-server-cors:$ktorVersion"
                implementation(ktor("content-negotiation")) // "io.ktor:ktor-server-content-negotiation:$ktorVersion"

                implementation(project(":fintrack-common"))
                implementation(project(":fintrack-api"))
                implementation(project(":fintrack-mapper"))
                implementation(project(":fintrack-stubs"))

                implementation("ch.qos.logback:logback-classic:$logbackVersion")
                implementation(ktor("call-logging"))

                implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$serializationVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")


                implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")

                implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion") // "io.ktor:ktor-server-auth:$ktorVersion"
            }
        }
        @Suppress("UNUSED_VARIABLE")
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        @Suppress("UNUSED_VARIABLE")
        val jvmMain by getting {
            dependencies {
                dependencies {
                    implementation(kotlin("stdlib-common"))
                    implementation(ktor("core")) // "io.ktor:ktor-server-core:$ktorVersion"
                    implementation(ktor("netty"))
                    implementation(ktor("caching-headers")) // "io.ktor:ktor-server-caching-headers:$ktorVersion"
                    implementation(ktor("cors")) // "io.ktor:ktor-server-cors:$ktorVersion"
                    implementation(ktor("content-negotiation")) // "io.ktor:ktor-server-content-negotiation:$ktorVersion"

                    implementation(project(":fintrack-common"))
                    implementation(project(":fintrack-api"))
                    implementation(project(":fintrack-mapper"))
                    implementation(project(":fintrack-stubs"))

                    implementation("ch.qos.logback:logback-classic:$logbackVersion")
                    implementation(ktor("call-logging"))

                    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$serializationVersion")
                    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")


                    implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
                    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")

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
