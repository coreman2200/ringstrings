plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("org.jetbrains.kotlin.android")
    //id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.coreman2200.ringstrings_ui"
    compileSdk = Ext.compile_sdk

    defaultConfig {
        minSdk = Ext.min_sdk
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility(Ext.javaVersion)
        targetCompatibility(Ext.javaVersion)
    }
    kotlinOptions {
        jvmTarget = Ext.jvmTarget

    }

    buildFeatures {
        viewBinding = true
    }

    testOptions.unitTests.isIncludeAndroidResources = true

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    presentation()
}