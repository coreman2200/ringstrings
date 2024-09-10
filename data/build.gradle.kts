plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("com.squareup.wire") version "5.0.0"
    id("org.jetbrains.kotlin.android")
    //id("dagger.hilt.android.plugin")
}

wire {
    sourcePath {
        srcDir("com/coreman2200/ringstrings/data/protos/gen")
        include("com/coreman2200/ringstrings/data/protos/gen/ringstrings.proto")
    }
    proto {
        out = "com/coreman2200/ringstrings/data/protos"
    }
    kotlin {
        android=true
    }
    java {
        android=true
    }
}

android {
    namespace = "com.coreman2200.ringstrings_data"
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
        sourceCompatibility(JavaVersion.VERSION_18)
        targetCompatibility(JavaVersion.VERSION_18)
    }
    kotlinOptions {
        jvmTarget = "18"
    }

    testOptions.unitTests.isIncludeAndroidResources = true

    sourceSets {
        getByName("main") {
            manifest.srcFile("src/main/AndroidManifest.xml")
            java.srcDirs("src/main/java")
            res.srcDirs("src/main/res")
        }

        getByName("test").resources.srcDirs("src/main")
        getByName("androidTest").assets.srcDirs("$projectDir/schemas")
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    data()
}