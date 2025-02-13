plugins {
    alias(libs.plugins.daongil.compose)
    alias(libs.plugins.daongil.android.hilt)
    alias(libs.plugins.navigation.safe.args)
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "kr.techit.lion.presentation"

    viewBinding {
        enable = true
    }
}

dependencies{
    implementation(projects.core.designsystem)
    implementation(projects.domain)

    implementation(libs.bundles.androidx)
    implementation(libs.bundles.coroutines)
    implementation(libs.bundles.naver)
    implementation(libs.bundles.google)
    implementation(libs.firebase.crashlytics.ktx)
    implementation(libs.kakao.user)

    implementation(libs.glide)

    implementation(libs.photoview)
    implementation(libs.flexbox)
    implementation(libs.circleindicator)
    implementation(libs.simple.rating.bar)
    implementation(libs.droidsonroids)
    implementation(libs.androidx.constraintlayout)
}