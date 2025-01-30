package kr.techit.lion.datastore.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.techit.lion.datastore.TokenDataSourceImpl
import kr.techit.lion.network.token.TokenDataSource

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {
    @Binds
    fun binsTokenDataSource(tokenDataSourceImpl: TokenDataSourceImpl): TokenDataSource
}