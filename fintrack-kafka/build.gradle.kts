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
val atomicfuVersion: String by project

plugins {
    id("application")
    kotlin("plugin.serialization")
    kotlin("multiplatform")

}

repositories {
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
    maven {
        url = uri("https://jitpack.io")
    }
    mavenCentral()
}

kotlin {
    jvm {}

    sourceSets {

        @Suppress("UNUSED_VARIABLE")
        val jvmMain by getting {
            dependencies {
                dependencies {
                    implementation(kotlin("stdlib-common"))
                    implementation(project(":fintrack-common"))
                    implementation(project(":fintrack-api"))
                    implementation(project(":fintrack-mapper"))
                    implementation(project(":fintrack-stubs"))
                    implementation(project(":fintrack-service"))
                    implementation("org.jetbrains.kotlinx:atomicfu:$atomicfuVersion")
                    implementation("com.github.IlyaKalashnikov:ktor-kafka-client:-SNAPSHOT")
                    implementation("org.apache.kafka:kafka-clients:$kafkaClientVersion")
                    implementation("org.apache.kafka:kafka-streams:$kafkaClientVersion")
                    implementation("ch.qos.logback:logback-classic:$logbackVersion")
                    implementation("io.github.microutils:kotlin-logging-jvm:$kotlinLoggingJvmVersion")
                    implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
                    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
                }
            }
        }
    }

}