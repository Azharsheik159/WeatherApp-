plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin") // Ap

}

android {
    namespace = "com.example.testtaskdemo"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.testtaskdemo"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packagingOptions {
        exclude ("META-INF/gradle/incremental.annotation.processors")
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.4")
    implementation("androidx.activity:activity-compose:1.9.1")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    //navigation
    implementation("androidx.navigation:navigation-compose:2.7.7")
    //retrofit
    implementation ("com.squareup.okhttp3:okhttp:4.9.2")

    implementation ("com.squareup.okhttp3:logging-interceptor:4.10.0")
//    implementation 'com.google.code.gson:gson:2.8.6'

    implementation ("com.squareup.retrofit:adapter-rxjava:2.0.0-beta1")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0")
    implementation ("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2")
    // annotation
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.google.code.gson:gson:2.8.5")
    implementation ("com.google.android.gms:play-services-location:21.2.0")
    implementation("com.google.dagger:hilt-android:2.48")
    implementation("com.google.dagger:hilt-android-compiler:2.48")
    kapt("com.google.dagger:hilt-android-compiler:2.48")
    implementation ("androidx.hilt:hilt-navigation-compose:1.2.0")
    annotationProcessor("androidx.room:room-compiler:2.5.0")
    //viewModel
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.5")
//loading
    implementation ("androidx.compose.material:material:1.4.0")
//coil
    implementation ("io.coil-kt:coil-compose:2.3.0")
    // Datastore
    implementation("androidx.datastore:datastore-preferences:1.0.0")
}