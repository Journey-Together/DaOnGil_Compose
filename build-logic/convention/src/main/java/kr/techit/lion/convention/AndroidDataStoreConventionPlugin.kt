package kr.techit.lion.convention

import kr.techit.lion.convention.extension.Plugins
import kr.techit.lion.convention.extension.applyPlugins
import kr.techit.lion.convention.extension.getLibrary
import kr.techit.lion.convention.extension.implementation
import kr.techit.lion.convention.extension.libs
import org.gradle.kotlin.dsl.dependencies

class AndroidDataStoreConventionPlugin : BuildLogicConventionPlugin (
    block = {
        applyPlugins(Plugins.KOTLINX_SERIALIZATION)

        dependencies {
            implementation(libs.getLibrary("androidx.datastore.preferences"))
            implementation(libs.getLibrary("kotlinx-serialization-json"))
        }
    }
)