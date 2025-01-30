package kr.techit.lion.datastore

import kotlinx.serialization.Serializable

@Serializable
sealed interface Activation {
    @Serializable
    data object Activate : Activation

    @Serializable
    data object DeActivate : Activation
}
