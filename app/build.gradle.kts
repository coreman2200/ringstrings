plugins {
    id("com.android.application")
    id("kotlin-android")
    id("org.jetbrains.kotlin.android")
    //id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
}

android {

    namespace = "com.coreman2200.ringstrings"
    compileSdk = Ext.compile_sdk
    defaultConfig {
        applicationId = "com.coreman2200.ringstrings"
        minSdk = Ext.min_sdk
        targetSdk = Ext.target_sdk
        versionCode = Ext.version_code
        versionName = Ext.version_name
        testApplicationId = "com.coreman2200.ringstrings"
    }

    sourceSets {
        getByName("main") {
            manifest.srcFile("src/main/AndroidManifest.xml")
            java.srcDirs("src/main/java")
            res.srcDirs("src/main/res")
        }

        getByName("androidTest").assets.srcDirs("$projectDir/schemas")
    }

    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_18)
        targetCompatibility(JavaVersion.VERSION_18)
    }

    kotlinOptions {
        jvmTarget = "18"
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }

    kapt {
        generateStubs = true
    }

    testOptions.unitTests.isIncludeAndroidResources = true

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    //compileOnly 'org.glassfish:javax.annotation:10.0-b28'
    app()
}