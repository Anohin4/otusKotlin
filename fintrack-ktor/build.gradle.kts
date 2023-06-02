import org.jetbrains.kotlin.util.suffixIfNot

buildscript {
    val atomicfuVersion: String by project
    dependencies {
        classpath("org.jetbrains.kotlinx:atomicfu-gradle-plugin:$atomicfuVersion")
    }
}

val ktorVersion: String by project
val logbackVersion: String by project
val serializationVersion: String by project
val koinVersion: String by project
val kafkaClientVersion: String by project
val kotlinLoggingJvmVersion: String by project
val coroutinesVersion: String by project
val kotestVersion: String by project
val testContainersVersion: String by project

plugins {
    id("application")
    kotlin("plugin.serialization")
    kotlin("multiplatform")
    id("io.kotest.multiplatform")
}

application {
    mainClass.set("ru.otus.otuskotlin.fintrack.app.ApplicationKt")
}

repositories {
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
    mavenCentral()
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
                    implementation("com.github.IlyaKalashnikov:ktor-kafka-client:-SNAPSHOT")
                    implementation("org.apache.kafka:kafka-clients:$kafkaClientVersion")
                    implementation("org.apache.kafka:kafka-streams:$kafkaClientVersion")
                    implementation("io.github.microutils:kotlin-logging-jvm:$kotlinLoggingJvmVersion")

                    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
                    implementation(project(":fintrack-common"))
                    implementation(project(":fintrack-api"))
                    implementation(project(":fintrack-mapper"))
                    implementation(project(":fintrack-stubs"))
                    implementation(project(":fintrack-service"))
                    implementation(project(":fintrack-ktor-plugin"))
                    implementation(project(":fintrack-kafka"))

                    implementation("ch.qos.logback:logback-classic:$logbackVersion")
                    implementation(ktor("call-logging"))

                    implementation("io.kotest.extensions:kotest-extensions-koin:1.1.0")
                    implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
                    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion") // "io.ktor:ktor-server-auth:$ktorVersion"
                }
            }
        }

        @Suppress("UNUSED_VARIABLE")
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation(ktor("test-host"))
                implementation(ktor("content-negotiation", prefix = "client-"))
                implementation("io.kotest.extensions:kotest-extensions-testcontainers:1.3.4")
                implementation("io.kotest:kotest-runner-junit5:${kotestVersion}")
                implementation("io.kotest:kotest-assertions-core:${kotestVersion}")
                implementation("io.kotest.extensions:kotest-extensions-koin:1.1.0")
                implementation("org.testcontainers:kafka:${testContainersVersion}")
                implementation("io.insert-koin:koin-test:${koinVersion}")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1")


            }
        }
    }
}

tasks {
    // Tests
    withType<Test>{
        useJUnitPlatform()
        //костыль для котеста и джавы 17
        jvmArgs("--add-opens","java.base/java.util=ALL-UNNAMED")
    }

}
