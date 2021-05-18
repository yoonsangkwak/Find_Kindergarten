package site.yoonsang.findkindergarten.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import site.yoonsang.findkindergarten.network.HttpRequestInterceptor
import site.yoonsang.findkindergarten.network.KindergartenApi
import site.yoonsang.findkindergarten.network.KindergartenLocationApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideKindergartenApi(): KindergartenApi =
        Retrofit.Builder()
            .baseUrl(KindergartenApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(KindergartenApi::class.java)

    @Singleton
    @Provides
    fun provideClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addNetworkInterceptor(HttpRequestInterceptor())
            .build()

    @Singleton
    @Provides
    fun provideKindergartenLocationApi(): KindergartenLocationApi =
        Retrofit.Builder()
            .baseUrl(KindergartenLocationApi.BASE_URL)
            .client(provideClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(KindergartenLocationApi::class.java)
}