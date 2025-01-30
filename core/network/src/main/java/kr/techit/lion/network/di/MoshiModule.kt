package kr.techit.lion.network.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.techit.lion.network.util.AedJsonAdapter
import kr.techit.lion.network.util.EmergencyMessageJsonAdapter
import kr.techit.lion.network.util.EmergencyRealtimeJsonAdapter
import kr.techit.lion.network.util.LocalDateAdapter
import retrofit2.converter.moshi.MoshiConverterFactory
import java.time.LocalDateTime
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object MoshiModule {
    @Provides
    @Singleton
    fun provideMoshi(): MoshiConverterFactory {
        val moshi = Moshi.Builder()
            .add(LocalDateTime::class.java, Rfc3339DateJsonAdapter().nullSafe())
            .add(LocalDateAdapter())
            .add(KotlinJsonAdapterFactory())
            .build()
        val factory = MoshiConverterFactory.create(moshi)
        return factory
    }

    @AedMoshi
    @Provides
    @Singleton
    fun provideAedMoshi(): Moshi {
        return Moshi.Builder()
            .add(AedJsonAdapter())
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @EmergencyMoshi
    @Provides
    @Singleton
    fun provideEmergencyMoshi(): Moshi {
        return Moshi.Builder()
            .add(EmergencyRealtimeJsonAdapter())
            .add(EmergencyMessageJsonAdapter())
            .add(KotlinJsonAdapterFactory())
            .build()
    }
}