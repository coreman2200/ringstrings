// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        mavenCentral()
        google()
    }

    dependencies {
        classpath("androidx.room:room-gradle-plugin:${Versions.roomVersion}")
        //classpath(Dependencies.hiltAgp)
    }
}
