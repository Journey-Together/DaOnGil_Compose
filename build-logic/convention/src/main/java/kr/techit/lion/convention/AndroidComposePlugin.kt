package kr.techit.lion.convention

import com.android.build.gradle.LibraryExtension
import kr.techit.lion.convention.extension.configureComposeAndroid
import kr.techit.lion.convention.extension.getLibrary
import kr.techit.lion.convention.extension.implementation
import kr.techit.lion.convention.extension.libs
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidComposePlugin : BuildLogicConventionPlugin(
  block = {
      extensions.configure<LibraryExtension> {
          configureComposeAndroid(this)
      }

      dependencies {
          implementation(libs.getLibrary("kotlinx.immutable"))
      }
  }
)