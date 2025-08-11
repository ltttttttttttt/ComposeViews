import PublishConfig.inceptionYear
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
    //https://github.com/vanniktech/gradle-maven-publish-plugin
    //https://www.jetbrains.com/help/kotlin-multiplatform-dev/multiplatform-publish-libraries.html#publish-to-maven-central-using-continuous-integration
    //https://central.sonatype.com/publishing/deployments
    id("com.vanniktech.maven.publish") version publishVersion
    kotlin("plugin.compose")
}


group = PublishConfig.group
version = "$composeVersion.1"

mavenPublishing {
    publishToMavenCentral()

    signAllPublications()

    coordinates(group.toString(), project.name, version.toString())

    pom {
        name = project.name
        description = PublishConfig.description
        inceptionYear = PublishConfig.inceptionYear
        url = PublishConfig.projectUrl
        licenses {
            license {
                name = "The Apache License, Version 2.0"
                url = "https://www.apache.org/licenses/LICENSE-2.0.txt"
                distribution = "https://www.apache.org/licenses/LICENSE-2.0.txt"
            }
        }
        developers {
            developer {
                id = "ltttttttttttt"
                name = "lt"
                email = "lt.dygzs@qq.com"
                url = "https://github.com/ltttttttttttt"
            }
        }
        scm {
            url = PublishConfig.projectUrl
        }
    }
}

kotlin {
    androidTarget {
        publishLibraryVariants("release")
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

    ohosArm64 {
        binaries.sharedLib {
            baseName = "kn"
            export("org.jetbrains.compose.export:export:$composeVersion")
        }

        val main by compilations.getting

        val resource by main.cinterops.creating {
            defFile(file("src/ohosArm64Main/cinterop/resource.def"))
            includeDirs(file("src/ohosArm64Main/cinterop/include"))
        }
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
            }
        }
        val androidUnitTest by getting {
            dependencies {
                implementation("junit:junit:4.13")
            }
        }

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

        val ohosArm64Main by getting {
            dependencies {
                api("org.jetbrains.compose.export:export:$composeVersion")
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
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}

//compose配置
compose {
    //kotlinCompilerPlugin.set("androidx.compose.compiler:compiler:$composeCompilerVersion")
}