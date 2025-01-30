package kr.techit.lion.database.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.techit.lion.database.MainDatabase
import kr.techit.lion.database.dao.AreaCodeDao
import kr.techit.lion.database.dao.RecentlySearchKeywordDAO
import kr.techit.lion.database.dao.SigunguCodeDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DaoModule {
    @Provides
    @Singleton
    fun provideAreaCodeDao(db: MainDatabase): AreaCodeDao = db.areaCodeDao()

    @Provides
    @Singleton
    fun provideSigunguCodeDao(db: MainDatabase): SigunguCodeDao = db.sigunguCodeDao()

    @Provides
    @Singleton
    fun provideRecentlySearchKeywordDao(db: MainDatabase): RecentlySearchKeywordDAO = db.recentlySearchKeywordDao()
}