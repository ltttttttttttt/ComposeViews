/*
 * Copyright lt 2022
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


plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose") version composeVersion
    id("com.android.library")
}

group = "com.lt.ltttttttttttt"

android {
    compileSdk = 33
    defaultConfig {
        minSdk = 21
        targetSdk = 31

        var testIndex = "-1"
        try {
            testIndex = File(project.rootDir, "test_index.txt").readText()
        } catch (e: Exception) {
        }
        buildConfigField("int", "TEST_INDEX", testIndex)
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    lint {
        checkDependencies = true//开启 lint 性能优化
        abortOnError = false//忽略Lint检查
        checkReleaseBuilds = false//压制警告,打包的时候有时候会有莫名其妙的警告
    }
}

kotlin {
    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }
    jvm("desktop") {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":core"))
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                api(project(":android"))
                implementation("androidx.activity:activity-compose:1.4.0")
                implementation("androidx.appcompat:appcompat:1.2.0")
                implementation("io.coil-kt:coil-compose:1.4.0")
            }
        }
        val androidTest by getting {
            dependencies {
                implementation("junit:junit:4.13.2")
            }
        }
        val desktopMain by getting {
            dependencies {
                api(project(":desktop_jvm"))
                api(compose.preview)
            }
        }
        val desktopTest by getting
    }
}