plugins {
    `kotlin-dsl`
}

group = "kr.techit.lion.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    compileOnly(libs.androidx.room.gradle.plugin)
    compileOnly(libs.ksp.gradle.plugin)
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
}

gradlePlugin {
    val conventionPluginClasses = listOf(
        "android.application" to "AndroidApplicationConventionPlugin",
        "android.compose" to "AndroidComposePlugin",
        "android.library" to "AndroidLibraryPlugin",
        "java.library" to "JavaLibraryPlugin",
        "android.room" to "AndroidRoomConventionPlugin",
        "hilt.library" to "HiltConventionPlugin",
        "android.firebase" to "AndroidApplicationFirebaseConventionPlugin",
        "android.moshi" to "MoshiConventionPlugin",
        "app" to "DaongilAppConventionPlugin",
        "data" to "DaongilDataConventionPlugin",
        "domain" to "DaongilDomainConventionPlugin",
        "presentation" to "DaongilPresentationConventionPlugin"
    )

    plugins {
        conventionPluginClasses.forEach { pluginClass ->
            pluginRegister(pluginClass)
        }
    }
}

fun NamedDomainObjectContainer<PluginDeclaration>.pluginRegister(data: Pair<String, String>) {
    val (pluginName, className) = data
    register(pluginName) {
        id = "daongil.$pluginName"
        implementationClass = "kr.techit.lion.convention.$className"
    }
}
