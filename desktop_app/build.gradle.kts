/*
 * Copyright lt 2023
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

group = "com.github.ltttttttttttt"

kotlin {
    jvm("desktop") {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
//        withJava() //https://issuetracker.google.com/issues/248593403?hl=ko
    }
    sourceSets {
        val commonMain by getting
        val commonTest by getting
        val desktopMain by getting {
            dependencies {
                implementation(project(":common_app")) {
                    exclude("org.jetbrains.kotlinx", "kotlinx-coroutines-android")//剔除安卓协程依赖
                }
                implementation(compose.desktop.currentOs)
            }
        }
        val desktopTest by getting
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        var testIndex = "-1"
        try {
            testIndex = File(project.rootDir, "test_index.txt").readText()
        } catch (e: Exception) {
        }
        args += listOf(testIndex)
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "jvm"
            packageVersion = "1.0.0"
        }
    }
}

dependencies {
}