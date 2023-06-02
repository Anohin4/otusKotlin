import org.jetbrains.kotlin.util.suffixIfNot

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

    fun ktor(
        module: String,
        prefix: String = "server-",
        version: String? = this@Build_gradle.ktorVersion
    ): Any =
        "io.ktor:ktor-${prefix.suffixIfNot("-")}$module:$version"

    sourceSets {
        @Suppress("UNUSED_VARIABLE")
        val jvmMain by getting {
            dependencies {
                dependencies {
                    implementation(kotlin("stdlib-common"))
                    implementation(ktor("core"))
                    implementation(ktor("netty"))
                    implementation("io.insert-koin:koin-ktor:$koinVersion")
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
