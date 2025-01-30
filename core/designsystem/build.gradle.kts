plugins {
    alias(libs.plugins.daongil.android.library)
    alias(libs.plugins.daongil.compose)
}

android {
    namespace = "kr.techit.lion.designsystem"
}

dependencies {
    implementation(libs.compose.coil)
}