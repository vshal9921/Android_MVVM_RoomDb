package com.kinected.myapplication.common

import android.content.Context
import android.content.SharedPreferences
import androidx.work.WorkManager
import com.kinected.myapplication.data.CountryDao
import com.kinected.myapplication.data.CountryDb
import com.kinected.myapplication.data.CountryRepo
import com.kinected.myapplication.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit{

        val httpLoggingInterceptor = HttpLoggingInterceptor().apply{
            level = HttpLoggingInterceptor.Level.BASIC
        }

        val httpClient = OkHttpClient().newBuilder().apply {
            addInterceptor(httpLoggingInterceptor)
        }

        return Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com")
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun providesCountryRepo(apiService: ApiService, countryDao: CountryDao, preferenceRepo: PreferenceRepo): CountryRepo{
        return CountryRepo(apiService, countryDao, preferenceRepo)
    }

    // Room Database
    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context): CountryDb{
        return CountryDb.getInstance(context)
    }

    @Provides
    @Singleton
    fun providesCountryDao(countryDb: CountryDb): CountryDao {
        return countryDb.countryDao()
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun providePreferenceRepo(sharedPreferences: SharedPreferences): PreferenceRepo {
        return PreferenceRepo(sharedPreferences)
    }

    @Provides
    fun provideWorkManager(@ApplicationContext context: Context): WorkManager {
        return WorkManager.getInstance(context)
    }
}