package com.amit.e_commerceapp.data.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.amit.e_commerceapp.data.local.AppDb
import com.amit.e_commerceapp.data.remote.ApiService
import com.amit.e_commerceapp.data.repos.ProductRepositoryImpl
import com.amit.e_commerceapp.domain.repos.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)


class ProductModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDb {
        return Room.databaseBuilder(context, AppDb::class.java, "app.db").build()
    }

    @Singleton
    @Provides
    fun provideCartDao(appDb: AppDb) = appDb.cardDao()

    @Singleton
    @Provides
    fun provideProductDao(appDb: AppDb) = appDb.productDao()

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl("https://example.com/api/").addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }


    @Provides
    @Singleton
    fun provideProductRepository(appDb: AppDb, apiService: ApiService): ProductRepository = ProductRepositoryImpl(apiService, appDb.productDao(), appDb.cardDao())

}