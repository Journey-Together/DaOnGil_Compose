package kr.techit.lion.convention

import kr.techit.lion.convention.extension.Plugins
import kr.techit.lion.convention.extension.applyPlugins
import kr.techit.lion.convention.extension.getBundle
import kr.techit.lion.convention.extension.getLibrary
import kr.techit.lion.convention.extension.implementation
import kr.techit.lion.convention.extension.libs
import org.gradle.kotlin.dsl.dependencies

internal class AndroidRetrofitConventionPlugin : BuildLogicConventionPlugin(
    block = {
        applyPlugins(Plugins.KOTLINX_SERIALIZATION)
        dependencies {
            implementation(libs.getBundle("network"))
            implementation(libs.getLibrary("kotlinx.serialization.json"))
        }
    }
)
