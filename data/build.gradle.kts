plugins {
    id("cleanarchitecture.android.library")
    id("cleanarchitecture.android.hilt")
}

android {
    namespace = "io.hydok.data"
}

dependencies {
    implementation(project(":domain"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
