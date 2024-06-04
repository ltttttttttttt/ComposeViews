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
    id("org.jetbrains.compose")
    id("com.android.application")
    kotlin("android")
    kotlin("plugin.compose")
}

android {
    compileSdk = 34
    buildToolsVersion = "30.0.3"

    defaultConfig {
        applicationId = "com.lt.test_compose"
        minSdk = 21
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    //签名设置
    signingConfigs {
        create("release") {
            //设置debug时用的签名和密码
            storeFile = File("${project.rootDir.absolutePath}/composeview.jks")
            storePassword = "11111111"//签名密码
            keyAlias = "key"//别名
            keyPassword = "11111111"//别名密码
            enableV2Signing = true//启用v2签名
            enableV1Signing = true//启用v1签名
        }
    }
    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = listOf("-Xallow-kotlin-package")
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        //kotlinCompilerExtensionVersion = composeCompilerVersion
    }
}

dependencies {
    implementation(project(":common_app"))
    implementation("androidx.core:core-ktx:1.3.2")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("com.google.android.material:material:1.3.0")

    implementation("io.coil-kt:coil-compose:1.4.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
    implementation("androidx.activity:activity-compose:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.0-beta02")
    testImplementation("junit:junit:4.+")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.0.1")
}