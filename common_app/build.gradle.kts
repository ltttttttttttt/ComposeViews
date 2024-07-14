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
    id("org.jetbrains.compose")
    id("com.android.library")
    id("com.google.devtools.ksp")
    kotlin("native.cocoapods")
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
    androidTarget {
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

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries {
        }
    }

    js(IR) {
        browser()
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        moduleName = "common_app"
        browser {
            commonWebpackConfig {
                outputFileName = "common_app.js"
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
        version = "0.0.1"
        summary = "Jatpack(JetBrains) Compose views"
        homepage = "https://github.com/ltttttttttttt/ComposeViews"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "common_app"
            isStatic = true
        }
//        extraSpecAttributes["resources"] =
//            "['../ComposeViews/resources/**', '../desktop_app/src/desktopMain/resources/**']"
    }
    sourceSets {
        all {
            languageSettings {
                optIn("org.jetbrains.compose.resources.ExperimentalResourceApi")
            }
        }
        val commonMain by getting {
            kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
            dependencies {
                api(project(":ComposeViews"))
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)
                api(compose.animation)
                api(compose.ui)
                implementation(compose.components.resources)//api不能生成Res?
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
                implementation("androidx.appcompat:appcompat:1.2.0")
                api("io.coil-kt.coil3:coil-compose:$coilVersion")//coil图片加载
                api("io.coil-kt.coil3:coil-network-ktor:$coilVersion")//图片网络请求引擎
            }
        }
        val androidUnitTest by getting {
            dependencies {
                implementation("junit:junit:4.13.2")
            }
        }

        val desktopMain by getting {
            dependencies {
                api(compose.preview)
                api("io.coil-kt.coil3:coil-compose:$coilVersion")//coil图片加载
                api("io.coil-kt.coil3:coil-network-ktor:$coilVersion")//图片网络请求引擎
            }
        }
        val desktopTest by getting

        val iosMain by creating {
            kotlin.srcDir("build/generated/ksp/ios/iosMain/kotlin")
            dependencies {
                dependsOn(commonMain)
                api("io.coil-kt.coil3:coil-compose:$coilVersion")//coil图片加载
                api("io.coil-kt.coil3:coil-network-ktor:$coilVersion")//图片网络请求引擎
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
                api("io.coil-kt.coil3:coil-compose:$coilVersion")//coil图片加载
                api("io.coil-kt.coil3:coil-network-ktor:$coilVersion")//图片网络请求引擎
            }
        }

        val wasmJsMain by getting {
            dependencies {
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
    ksp {
        arg("packageListWithVirtualReflection", "com.lt.common_app")
    }
}

dependencies {
    add("kspCommonMainMetadata", "io.github.ltttttttttttt:VirtualReflection:1.3.1")
}