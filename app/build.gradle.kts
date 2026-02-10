plugins {
    id("cleanarchitecture.android.application")
    id("cleanarchitecture.android.compose")
    id("cleanarchitecture.android.hilt")
}

android {
    namespace = "io.hydok.cleanarchitecture"

    defaultConfig {
        applicationId = "io.hydok.cleanarchitecture"
        versionCode = 1
        versionName = "1.0.0"

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
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":presentation"))

    // Compose
    implementation(platform(libs.compose.bom))
    implementation(libs.activity.compose)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
