package kr.techit.lion.network.di

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.techit.lion.network.BuildConfig
import kr.techit.lion.network.service.AedService
import kr.techit.lion.network.service.AuthService
import kr.techit.lion.network.service.BookmarkService
import kr.techit.lion.network.service.EmergencyService
import kr.techit.lion.network.service.KorWithService
import kr.techit.lion.network.service.MemberService
import kr.techit.lion.network.service.NaverMapService
import kr.techit.lion.network.service.PharmacyService
import kr.techit.lion.network.service.PlaceService
import kr.techit.lion.network.service.PlanService
import kr.techit.lion.network.service.ReportService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun provideReportService(retrofit: Retrofit): ReportService {
        return retrofit.create(ReportService::class.java)
    }

    @Provides
    @Singleton
    fun providePlanService(retrofit: Retrofit): PlanService {
        return retrofit.create(PlanService::class.java)
    }

    @Singleton
    @Provides
    fun provideAuthService(retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }

    @Provides
    @Singleton
    fun provideMemberService(retrofit: Retrofit): MemberService {
        return retrofit.create(MemberService::class.java)
    }

    @Provides
    @Singleton
    fun providePlaceService(retrofit: Retrofit): PlaceService {
        return retrofit.create(PlaceService::class.java)
    }

    @Provides
    @Singleton
    fun provideBookmarkService(retrofit: Retrofit): BookmarkService {
        return retrofit.create(BookmarkService::class.java)
    }

    @Singleton
    @Provides
    fun provideNaverMapService(@NaverMap okHttpClient: OkHttpClient): NaverMapService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.NAVER_MAP_BASE)
            .addConverterFactory(MoshiConverterFactory.create().asLenient())
            .client(okHttpClient)
            .build()
            .create()
    }

    @Singleton
    @Provides
    fun provideKorWithService(okHttpClient: OkHttpClient): KorWithService {
        val baseUrl = "https://apis.data.go.kr/B551011/KorWithService1/"
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create().asLenient())
            .client(okHttpClient)
            .build()
            .create()
    }

    @Singleton
    @Provides
    fun provideAedService(
        okHttpClient: OkHttpClient,
        @AedMoshi moshi: Moshi
    ): AedService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.AED_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
            .client(okHttpClient)
            .build()
            .create()
    }

    @Singleton
    @Provides
    fun provideEmergencyService(
        okHttpClient: OkHttpClient,
        @EmergencyMoshi moshi: Moshi
    ): EmergencyService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.EMERGENCY_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
            .client(okHttpClient)
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun providePharmacyService(okHttpClient: OkHttpClient): PharmacyService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.PHARMACY_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create().asLenient())
            .client(okHttpClient)
            .build()
            .create()
    }
}