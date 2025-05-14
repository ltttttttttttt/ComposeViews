import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

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
    id("org.jetbrains.compose")
    id("com.android.library")
    //id("maven-publish")
    id("convention.publication")
    kotlin("plugin.compose")
    id("com.vk.vkompose")
}

if (vkomposeIsCheck)
    vkompose {
        skippabilityCheck = true

        recompose {
            isHighlighterEnabled = true
            isLoggerEnabled = true
        }

        testTag {
            isApplierEnabled = true
            isDrawerEnabled = true
            isCleanerEnabled = true
        }

        sourceInformationClean = true
    }

group = "io.github.ltttttttttttt"
//上传到mavenCentral命令: ./gradlew publishAllPublicationsToSonatypeRepository
//mavenCentral后台: https://s01.oss.sonatype.org/#stagingRepositories
version = "$composeVersion.1"

kotlin {
    androidTarget {
        publishLibraryVariants("debug", "release")
    }

    jvm("desktop") {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        //kotlin引入静态库参考: https://kotlinlang.org/docs/multiplatform-dsl-reference.html#cinterops
        iosTarget.compilations.all {
        }
    }

    js(IR) {
        browser()
        compilations.all {
        }
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        moduleName = "ComposeViews"
        browser {
            commonWebpackConfig {
                outputFileName = "ComposeViews.js"
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static = (static ?: mutableListOf()).apply {
                        // Serve sources to debug inside browser
                        add(project.projectDir.path)
                    }
                }
            }
        }
        binaries.executable()
    }

    cocoapods {
        summary = "Jatpack(JetBrains) Compose views"
        homepage = "https://github.com/ltttttttttttt/ComposeViews"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "ComposeViews"
            isStatic = true
        }
//        extraSpecAttributes["resources"] =
//            "['resources/**']"
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
                api(compose.components.resources)
                api("io.github.ltttttttttttt:DataStructure:1.1.4")
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
                //协程
                api("org.jetbrains.kotlinx:kotlinx-coroutines-swing:$coroutinesVersion")
            }
        }
        val desktopTest by getting

        val iosMain by creating {
            dependencies {
                dependsOn(commonMain)
            }
        }
        val iosTest by creating
        val iosSimulatorArm64Main by getting {
            dependsOn(iosMain)
        }
        val iosSimulatorArm64Test by getting {
            dependsOn(iosTest)
        }
        val iosArm64Main by getting {
            dependsOn(iosMain)
        }
        val iosArm64Test by getting {
            dependsOn(iosTest)
        }
        val iosX64Main by getting {
            dependsOn(iosMain)
        }
        val iosX64Test by getting {
            dependsOn(iosTest)
        }

        val jsMain by getting {
            dependencies {
            }
        }

        val wasmJsMain by getting {
            dependencies {
            }
        }
    }
}

android {
    namespace = "com.lt.compose_views"
    compileSdk = 35
    defaultConfig {
        minSdk = 21
        sourceSets["main"].manifest.srcFile("src/main/AndroidManifest.xml")

        consumerProguardFiles("consumer-rules.pro")
    }
    lint {
        targetSdk = 35
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

//compose配置
compose {
    //kotlinCompilerPlugin.set("androidx.compose.compiler:compiler:$composeCompilerVersion")
}

afterEvaluate {
    try {
        tasks.findByName("signAndroidReleasePublication")!!
            .mustRunAfter(tasks.findByName("publishAndroidDebugPublicationToSonatypeRepository"))
        tasks.findByName("signIosArm64Publication")!!
            .mustRunAfter(tasks.findByName("publishAndroidDebugPublicationToSonatypeRepository"))
        tasks.findByName("signIosSimulatorArm64Publication")!!
            .mustRunAfter(tasks.findByName("publishAndroidDebugPublicationToSonatypeRepository"))
        tasks.findByName("signIosX64Publication")!!
            .mustRunAfter(tasks.findByName("publishAndroidDebugPublicationToSonatypeRepository"))
        tasks.findByName("signJsPublication")!!
            .mustRunAfter(tasks.findByName("publishAndroidDebugPublicationToSonatypeRepository"))
        tasks.findByName("signKotlinMultiplatformPublication")!!
            .mustRunAfter(tasks.findByName("publishAndroidDebugPublicationToSonatypeRepository"))
        tasks.findByName("signIosArm64Publication")!!
            .mustRunAfter(tasks.findByName("publishAndroidReleasePublicationToSonatypeRepository"))
        tasks.findByName("signIosSimulatorArm64Publication")!!
            .mustRunAfter(tasks.findByName("publishIosArm64PublicationToSonatypeRepository"))
        tasks.findByName("signIosSimulatorArm64Publication")!!
            .mustRunAfter(tasks.findByName("publishAndroidReleasePublicationToSonatypeRepository"))
        tasks.findByName("signIosX64Publication")!!
            .mustRunAfter(tasks.findByName("publishIosArm64PublicationToSonatypeRepository"))
        tasks.findByName("signIosX64Publication")!!
            .mustRunAfter(tasks.findByName("publishAndroidReleasePublicationToSonatypeRepository"))
        tasks.findByName("signJsPublication")!!
            .mustRunAfter(tasks.findByName("publishIosArm64PublicationToSonatypeRepository"))
        tasks.findByName("signJsPublication")!!
            .mustRunAfter(tasks.findByName("publishAndroidReleasePublicationToSonatypeRepository"))
        tasks.findByName("signKotlinMultiplatformPublication")!!
            .mustRunAfter(tasks.findByName("publishIosArm64PublicationToSonatypeRepository"))
        tasks.findByName("signKotlinMultiplatformPublication")!!
            .mustRunAfter(tasks.findByName("publishAndroidReleasePublicationToSonatypeRepository"))
        tasks.findByName("signIosX64Publication")!!
            .mustRunAfter(tasks.findByName("publishIosSimulatorArm64PublicationToSonatypeRepository"))
        tasks.findByName("signJsPublication")!!
            .mustRunAfter(tasks.findByName("publishIosSimulatorArm64PublicationToSonatypeRepository"))
        tasks.findByName("signKotlinMultiplatformPublication")!!
            .mustRunAfter(tasks.findByName("publishIosSimulatorArm64PublicationToSonatypeRepository"))
        tasks.findByName("signJsPublication")!!
            .mustRunAfter(tasks.findByName("publishIosX64PublicationToSonatypeRepository"))
        tasks.findByName("signKotlinMultiplatformPublication")!!
            .mustRunAfter(tasks.findByName("publishIosX64PublicationToSonatypeRepository"))
        tasks.findByName("signKotlinMultiplatformPublication")!!
            .mustRunAfter(tasks.findByName("publishJsPublicationToSonatypeRepository"))
        tasks.findByName("signWasmJsPublication")!!
            .mustRunAfter(tasks.findByName("publishKotlinMultiplatformPublicationToSonatypeRepository"))
        tasks.findByName("signWasmJsPublication")!!
            .mustRunAfter(tasks.findByName("publishJsPublicationToSonatypeRepository"))
        tasks.findByName("signWasmJsPublication")!!
            .mustRunAfter(tasks.findByName("publishIosX64PublicationToSonatypeRepository"))
        tasks.findByName("signWasmJsPublication")!!
            .mustRunAfter(tasks.findByName("publishIosSimulatorArm64PublicationToSonatypeRepository"))
        tasks.findByName("signAndroidDebugPublication")!!
            .mustRunAfter(tasks.findByName("publishDesktopPublicationToSonatypeRepository"))
        tasks.findByName("signAndroidReleasePublication")!!
            .mustRunAfter(tasks.findByName("publishDesktopPublicationToSonatypeRepository"))
        tasks.findByName("signIosArm64Publication")!!
            .mustRunAfter(tasks.findByName("publishDesktopPublicationToSonatypeRepository"))
        tasks.findByName("signIosSimulatorArm64Publication")!!
            .mustRunAfter(tasks.findByName("publishDesktopPublicationToSonatypeRepository"))
        tasks.findByName("signIosX64Publication")!!
            .mustRunAfter(tasks.findByName("publishDesktopPublicationToSonatypeRepository"))
        tasks.findByName("signJsPublication")!!
            .mustRunAfter(tasks.findByName("publishDesktopPublicationToSonatypeRepository"))
        tasks.findByName("signKotlinMultiplatformPublication")!!
            .mustRunAfter(tasks.findByName("publishDesktopPublicationToSonatypeRepository"))
        tasks.findByName("signWasmJsPublication")!!
            .mustRunAfter(tasks.findByName("publishDesktopPublicationToSonatypeRepository"))
    } catch (ignore: Exception) {
    }
}