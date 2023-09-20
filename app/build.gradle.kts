plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs")
    id ("kotlin-kapt")
}

android {
    namespace = "com.aryasurya.githubapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.aryasurya.githubapp"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "KEY", "\"token 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\"")
    }

    buildTypes {
        debug {
            buildConfigField ("boolean", "DEBUG", "true")
        }
        release {
            buildConfigField ("boolean", "DEBUG", "false")
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation("de.hdodenhof:circleimageview:3.1.0")
    implementation("androidx.activity:activity-ktx:1.7.2")
    implementation("androidx.viewpager2:viewpager2:1.0.0")

    //testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //room
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.room:room-runtime:2.6.0-beta01")
    kapt("androidx.room:room-compiler:2.5.0-alpha02")

    //retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

    //coroutine support
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2") //viewModelScope
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2") //liveData
    implementation("androidx.room:room-ktx:2.5.2")
}