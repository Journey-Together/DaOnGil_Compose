package kr.techit.lion.database.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kr.techit.lion.database.MainDatabase
import kr.techit.lion.database.dao.AreaCodeDao
import kr.techit.lion.database.dao.SigunguCodeDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {
    @Provides
    @Singleton
    fun provideMainDatabase(@ApplicationContext appContext: Context): MainDatabase =
        Room.databaseBuilder(appContext, MainDatabase::class.java, "main_database")
            .fallbackToDestructiveMigration()
            .build()
}