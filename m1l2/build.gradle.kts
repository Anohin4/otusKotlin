plugins {
    kotlin("multiplatform")
}


kotlin {
    jvm {}
    js(BOTH) {
        browser()
    }


    val coroutinesVersion: String by project
    val jUnitJupiterVersion: String by project

    sourceSets {

        val commonTest by getting {
            dependencies {

            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit5"))

                implementation("org.junit.jupiter:junit-jupiter-params:$jUnitJupiterVersion")
            }
        }

    }
}

tasks {
    withType<Test>().configureEach {
        useJUnitPlatform {
//            includeTags.add("sampling")
        }
        filter {
            isFailOnNoMatchingTests = false
        }
        testLogging {
            showExceptions = true
            showStandardStreams = true
            events = setOf(org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED, org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED)
            exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
        }
    }
}