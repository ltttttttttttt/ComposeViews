plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose") version composeVersion
}

kotlin {
    js(IR) {
        browser()
        binaries.executable()
    }
    sourceSets {
        val jsMain by getting  {
            dependencies {
                implementation(project(":common_app"))
            }
        }
    }
}

compose.experimental {
    web.application {}
}

