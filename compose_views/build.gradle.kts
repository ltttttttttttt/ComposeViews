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
    android {
        publishLibraryVariants("release")
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
                //跨平台compose
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("androidx.activity:activity-compose:1.4.0")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.3")
            }
        }
        val androidTest by getting {
            dependencies {
                implementation("junit:junit:4.13")
            }
        }
        val desktopMain by getting {
            dependencies {
                //compose
                implementation(compose.preview)
                //desktop图片加载器
                api("com.github.ltttttttttttt:load-the-image:1.0.5")
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
    sourceSets["main"].res.srcDir("src/desktopMain/resources")
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