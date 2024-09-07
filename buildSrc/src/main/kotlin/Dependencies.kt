import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.project

/* Variables */
object Ext {
    const val debugBuild = "RingStrings-debug"
    const val build_tools = "30.0.2"
    const val compile_sdk = 33
    const val min_sdk = 24
    const val target_sdk = 33
    const val version_code = 1
    const val version_name = "1.0"
    const val agp_version = "7.0.4"
}

object Dependencies {

    // App
    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp3Version}" //impl
    const val okhttpUrl = "com.squareup.okhttp3:okhttp-urlconnection:${Versions.okhttp3Version}" //impl
    const val okhttpLogging = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp3Version}" // impl
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofitVersion}"


    const val mockwebserver = "com.squareup.okhttp3:mockwebserver:${Versions.okhttp3Version}" // testImplementation
    const val junit = "junit:junit:${Versions.junitVersion}" // testImplementation
    const val assertj = "org.assertj:assertj-core:${Versions.assertjVersion}" //testImplementation
    const val robolectric = "org.robolectric:robolectric:${Versions.robolectricVersion}" //testImplementation

    // Data
    const val datastore = "androidx.datastore:datastore:${Versions.datastoreVersion}" //implementation
    const val gson = "com.google.code.gson:gson:${Versions.gsonVersion}" //implementation
    const val wire = "com.squareup.wire:wire-runtime:${Versions.wireVersion}" //api
    const val ktxstdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlinVersion}" //implementation
    const val roomRuntime = "androidx.room:room-runtime:${Versions.roomVersion}" // implementation
    const val roomKtx = "androidx.room:room-ktx:${Versions.roomVersion}" // implementation
    const val roomCompiler = "androidx.room:room-compiler:${Versions.roomVersion}" // kapt

    // Domain
    const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesVersion}" // api
    const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesVersion}" // api

    // Presentation
    const val coreKtx = "androidx.core:core-ktx:${Versions.coreVersion}" // implementation
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompatVersion}" // implementation
    const val fragmentKtx = "androidx.fragment:fragment-ktx:${Versions.fragmentVersion}" // implementation
    const val material = "com.google.android.material:material:${Versions.materialVersion}" // implementation
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintlayoutVersion}" // implementation
    const val constraintLayoutCompose = "androidx.constraintlayout:constraintlayout-compose:${Versions.constraintlayoutComposeVersion}" // implementation
    const val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycleVersion}" // implementation
    const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleVersion}" // implementation
    const val lifecycleLiveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycleVersion}" // implementation

    const val composeMaterial = "androidx.compose.material3:material3:${Versions.composeMaterial3Version}"
    const val composeUi = "androidx.compose.ui:ui:${Versions.composeVersion}"
    const val composeUiGraphics = "androidx.compose.ui:ui-graphics:${Versions.composeVersion}"
    const val composeUiTooling = "androidx.compose.ui:ui-tooling:${Versions.composeVersion}"
    const val composeUiToolingPreview = "androidx.compose.ui:ui-tooling-preview:${Versions.composeVersion}"
    const val composeRuntime = "androidx.compose.runtime:runtime:${Versions.composeVersion}"

    // Dagger
    const val dagger = "com.google.dagger:dagger:${Versions.daggerVersion}" // api
    const val daggerAndroid = "com.google.dagger:dagger-android:${Versions.daggerVersion}" // api
    const val daggerAndroidSupport = "com.google.dagger:dagger-android-support:${Versions.daggerAndroidSupportVersion}" // api
    const val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.daggerVersion}" // kapt
    const val daggerAndroidProcessor = "com.google.dagger:dagger-android-processor:${Versions.daggerVersion}" // kapt
    const val daggerCompilerAnnotation = "com.google.dagger:dagger-compiler:${Versions.daggerVersion}" //annotationProcessor
    const val daggerAndroidAnnotationProcessor = "com.google.dagger:dagger-android-processor:${Versions.daggerVersion}" // annotationProcessor

    // Hilt
    const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hiltVersion}"
    const val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hiltVersion}"
    const val hiltAgp = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hiltVersion}"
}

fun DependencyHandler.app() {
    implementation(Dependencies.coreKtx)
    implementation(Dependencies.ktxstdlib)
    room()
    okhttp()
    dagger()
    hilt()
    compose()
    coroutines()
    testing()
    // other modules
    domainModule()
    dataModule()
    presenterModule()
}

fun DependencyHandler.domain() {
    implementation(Dependencies.coreKtx)
    implementation(Dependencies.wire)
    room()
    coroutines()
    testing()
    dagger()
    hilt()
    dataModule()
}

fun DependencyHandler.data() {
    implementation(Dependencies.coreKtx)
    implementation(Dependencies.ktxstdlib)
    implementation(Dependencies.datastore)
    implementation(Dependencies.wire)
    implementation(Dependencies.gson)
    room()
    coroutines()
    testing()
    dagger()
    hilt()
}

fun DependencyHandler.presentation() {
    implementation(Dependencies.material)
    implementation(Dependencies.fragmentKtx)
    implementation(Dependencies.appcompat)
    lifecycle()
    compose()
    dagger()
    hilt()
    coroutines()
    testing()
    domainModule()
}

fun DependencyHandler.testing() {
    testImplementation(Dependencies.mockwebserver)
    testImplementation(Dependencies.junit)
    testImplementation(Dependencies.assertj)
    testImplementation(Dependencies.robolectric)
}

fun DependencyHandler.dagger() {
    implementation(Dependencies.dagger)
    implementation(Dependencies.daggerAndroid)
    implementation(Dependencies.daggerAndroidSupport)
    kapt(Dependencies.daggerCompiler)
    kapt(Dependencies.daggerAndroidProcessor)
    kapt(Dependencies.daggerCompilerAnnotation)
    kapt(Dependencies.daggerAndroidAnnotationProcessor)
}

fun DependencyHandler.hilt() {
    implementation(Dependencies.hiltAndroid)
    kapt(Dependencies.hiltCompiler)
}

fun DependencyHandler.compose() {
    implementation(Dependencies.composeUi)
    implementation(Dependencies.composeRuntime)
    implementation(Dependencies.composeUiGraphics)
    implementation(Dependencies.composeUiTooling)
    implementation(Dependencies.composeMaterial)
    debugImplementation(Dependencies.composeUiToolingPreview)
}

fun DependencyHandler.room() {
    implementation(Dependencies.roomRuntime)
    implementation(Dependencies.roomKtx)
    kapt(Dependencies.roomCompiler)
}

fun DependencyHandler.lifecycle() {
    implementation(Dependencies.lifecycleViewModel)
    implementation(Dependencies.lifecycleRuntime)
    implementation(Dependencies.lifecycleLiveData)
}

fun DependencyHandler.okhttp() {
    implementation(Dependencies.retrofit)
    implementation(Dependencies.okhttp)
    implementation(Dependencies.okhttpUrl)
    implementation(Dependencies.okhttpLogging)
}

fun DependencyHandler.coroutines() {
    implementation(Dependencies.coroutinesCore)
    implementation(Dependencies.coroutinesAndroid)
}

fun DependencyHandler.constraintLayout() {
    implementation(Dependencies.constraintLayout)
    implementation(Dependencies.constraintLayoutCompose)
}

fun DependencyHandler.domainModule() {
    implementation(project(":domain"))

}

fun DependencyHandler.dataModule() {
    implementation(project(":data"))
}

fun DependencyHandler.presenterModule() {
    implementation(project(":presentation"))
}