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
    id("maven-publish")
}

group = "com.github.ltttttttttttt"
version = "1.0.0"

kotlin {
    metadata {
        compilations.all {
            kotlinOptions {
                freeCompilerArgs = listOf(
                    "-Xallow-kotlin-package",//设置可以使用kotlin开头的包名
                    "-opt-in=kotlin.RequiresOptIn",//设置可以使用RequiresOptIn注解(用于提示用户该api有问题)
                    "-Xbackend-threads=0",//并行编译kotlin
                    "-Xcontext-receivers",//启用context receiver
                )
            }
        }
    }
    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
                freeCompilerArgs = listOf(
                    "-Xallow-kotlin-package",//设置可以使用kotlin开头的包名
                    "-opt-in=kotlin.RequiresOptIn",//设置可以使用RequiresOptIn注解(用于提示用户该api有问题)
                    "-Xbackend-threads=0",//并行编译kotlin
                    "-Xcontext-receivers",//启用context receiver
                )
            }
        }
    }
    jvm("desktop") {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
                freeCompilerArgs = listOf(
                    "-Xallow-kotlin-package",//设置可以使用kotlin开头的包名
                    "-opt-in=kotlin.RequiresOptIn",//设置可以使用RequiresOptIn注解(用于提示用户该api有问题)
                    "-Xbackend-threads=0",//并行编译kotlin
                    "-Xcontext-receivers",//启用context receiver
                )
            }
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                //跨平台compose
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.3")
            }
        }
        val commonTest by getting
        val androidMain by getting {
            dependencies {
                implementation("androidx.activity:activity-compose:1.4.0")
            }
        }
        val androidTest by getting
        val desktopMain by getting {
            dependencies {
                //compose
                implementation(compose.preview)
            }
        }
        val desktopTest by getting
    }
}

android {
    compileSdk = 31
    defaultConfig {
        minSdk = 21
        targetSdk = 28
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

//publishing {
//    publications {
//        create("maven_public", MavenPublication::class) {
//            groupId = "com.github.ltttttttttttt"
//            artifactId = "library"
//            version = "1.0.0"
//            from(components.getByName("java"))
//        }
//    }
//}

afterEvaluate {
    publishing {
        publications {
            create("maven_public", MavenPublication::class) {
                groupId = "com.github.ltttttttttttt"
                artifactId = "library"
                version = "1.0.0"
                from(components.getByName("release"))
            }
        }
    }
}