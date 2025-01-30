plugins {
    alias(libs.plugins.daongil.android.library)
    alias(libs.plugins.daongil.android.hilt)
    alias(libs.plugins.daongil.room)
}

android {
    namespace = "kr.techit.lion.database"
}

dependencies {
    implementation(libs.gson)
}