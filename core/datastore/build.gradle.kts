plugins {
    alias(libs.plugins.daongil.android.library)
    alias(libs.plugins.daongil.datastore)
    alias(libs.plugins.daongil.android.hilt)
}

android {
    namespace = "kr.techit.lion.datastore"
}

dependencies {
    implementation(projects.core.network)
}
