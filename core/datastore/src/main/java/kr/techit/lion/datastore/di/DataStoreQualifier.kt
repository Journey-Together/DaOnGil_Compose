package kr.techit.lion.datastore.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class TokenDataStore

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ActivationDataStore