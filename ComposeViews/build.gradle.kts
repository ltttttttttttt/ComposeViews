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

plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("org.jetbrains.compose") version composeVersion
    id("com.android.library")
    //id("maven-publish")
    id("convention.publication")
}

group = "io.github.ltttttttttttt"
//上传到mavenCentral命令: ./gradlew publishAllPublicationsToSonatypeRepository
//mavenCentral后台: https://s01.oss.sonatype.org/#stagingRepositories
version = "1.4.0"

kotlin {
    android {
        publishLibraryVariants("debug", "release")
    }

    jvm("desktop") {
        compilations.all {
            defaultSourceSet.resources.srcDir("/resources")
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }

    ios()
    iosSimulatorArm64()

    js(IR) {
        browser()
        compilations.all {
            defaultSourceSet.resources.srcDir("/resources")
        }
    }

//    macosX64 {
//        binaries {
//            executable {
//                entryPoint = "main"
//            }
//        }
//    }
//    macosArm64 {
//        binaries {
//            executable {
//                entryPoint = "main"
//            }
//        }
//    }

    cocoapods {
        summary = "Jatpack(JetBrains) Compose views"
        homepage = "https://github.com/ltttttttttttt/ComposeViews"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "ComposeViews"
            isStatic = true
        }
        extraSpecAttributes["resources"] =
            "['resources/**']"
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                //跨平台compose
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)
                api(compose.animation)
                api(compose.ui)
                api("io.github.ltttttttttttt:DataStructure:1.0.12")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        val androidMain by getting {
            dependencies {
                api("androidx.activity:activity-compose:1.4.0")
                //协程
                api("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")
            }
        }
        val androidUnitTest by getting {
            dependencies {
                implementation("junit:junit:4.13")
            }
        }

        val desktopMain by getting {
            dependencies {
                //api(project(":ComposeViews")) {
                //    exclude("org.jetbrains.kotlinx", "kotlinx-coroutines-android")//剔除安卓协程依赖
                //}
                //desktop图片加载器
                api("com.github.ltttttttttttt:load-the-image:1.0.5")
                //协程
                api("org.jetbrains.kotlinx:kotlinx-coroutines-swing:$coroutinesVersion")
            }
        }
        val desktopTest by getting

        val iosMain by getting{
            dependencies {
                api("org.jetbrains.compose.components:components-resources:$composeVersion")
            }
        }
        val iosTest by getting
        val iosSimulatorArm64Main by getting {
            dependsOn(iosMain)
        }
        val iosSimulatorArm64Test by getting {
            dependsOn(iosTest)
        }

        val jsMain by getting {
            dependencies {
                api("org.jetbrains.compose.components:components-resources:$composeVersion")
            }
        }

//        val macosMain by creating {
//            dependsOn(commonMain)
//        }
//        val macosX64Main by getting {
//            dependsOn(macosMain)
//        }
//        val macosArm64Main by getting {
//            dependsOn(macosMain)
//        }
    }
}

android {
    compileSdk = 33
    defaultConfig {
        minSdk = 21
        targetSdk = 31
        sourceSets["main"].manifest.srcFile("src/main/AndroidManifest.xml")
        sourceSets["main"].res.srcDir("resources")
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

//compose配置
compose {
    kotlinCompilerPlugin.set("androidx.compose.compiler:compiler:$composeCompilerVersion")
}

//publishing {
//    publications {
//        create("maven_core", MavenPublication::class) {
//            groupId = "com.github.ltttttttttttt"
//            artifactId = "maven_core"
//            version = "1.0.0"
//            from(components.getByName("kotlin"))
//        }
//    }
//}

//afterEvaluate {
//    publishing {
//        publications {
//            create("maven_public", MavenPublication::class) {
//                groupId = "com.github.ltttttttttttt"
//                artifactId = "library"
//                version = "1.0.0"
//                from(components.getByName("release"))
//            }
//        }
//    }
//}
