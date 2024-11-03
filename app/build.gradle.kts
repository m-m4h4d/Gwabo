plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.gwabo"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.gwabo"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.activity)
    implementation(libs.picasso)
    implementation(libs.recyclerview)
    implementation(libs.retrofit.core)     // Retrofit core library
    implementation(libs.retrofit.gson)     // Retrofit Gson converter
    implementation(libs.okhttp)            // OkHttp for networking (optional)
    implementation(libs.okhttp.logging)    // OkHttp Logging Interceptor (optional)
    implementation(libs.glide)
    implementation(libs.glide.compiler)
    implementation(libs.ui.text.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}