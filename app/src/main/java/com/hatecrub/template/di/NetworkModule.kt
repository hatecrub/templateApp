package com.hatecrub.template.di

import com.google.gson.Gson
import com.hatecrub.template.data.RemoteApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    internal fun provideRemoteApi(client: OkHttpClient, gson: GsonConverterFactory): RemoteApi {
        val retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(gson)
            .build()

        return retrofit.create(RemoteApi::class.java)
    }

    @Singleton
    @Provides
    internal fun provideHttpClient(logger: HttpLoggingInterceptor): OkHttpClient {
        val builder = OkHttpClient()
            .newBuilder()
            .addInterceptor(logger)
        return builder.build()
    }

    @Singleton
    @Provides
    internal fun provideInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    @Singleton
    @Provides
    internal fun provideGson(): Gson {
        return Gson()
    }

    @Singleton
    @Provides
    internal fun provideGsonClient(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }

    companion object {
        private const val BASE_URL = "https://base.url/api/"
    }
}
