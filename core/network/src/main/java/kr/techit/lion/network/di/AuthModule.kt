package kr.techit.lion.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.techit.lion.network.service.AuthService
import kr.techit.lion.network.service.authentication.AuthAuthenticator
import kr.techit.lion.network.service.authentication.AuthInterceptor
import kr.techit.lion.network.token.TokenDataSource
import okhttp3.Authenticator
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {
    @Singleton
    @Provides
    fun provideAuthInterceptor(tokenDataSource: TokenDataSource): AuthInterceptor = AuthInterceptor(tokenDataSource)

    @Singleton
    @Provides
    fun provideAuthenticator(
        tokenDataSource: TokenDataSource,
        authService: Provider<AuthService>
    ): Authenticator {
        return AuthAuthenticator(tokenDataSource, authService)
    }
}