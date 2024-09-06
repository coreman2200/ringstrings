plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.coreman2200.ringstrings_domain"
    compileSdk = Ext.compile_sdk

    defaultConfig {
        minSdk = Ext.min_sdk
        targetSdk = Ext.target_sdk

        //testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
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

    sourceSets {
        getByName("test").resources.srcDirs("src/main")
    }

    kotlinOptions {
        jvmTarget = "18"
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(files("libs/swisseph-2.01.00-00.jar"))
    domain()
}
