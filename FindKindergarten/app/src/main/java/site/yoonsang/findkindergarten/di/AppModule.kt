package site.yoonsang.findkindergarten.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import site.yoonsang.findkindergarten.network.KindergartenApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(KindergartenApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Singleton
    @Provides
    fun provideKindergartenApi(retrofit: Retrofit): KindergartenApi =
        retrofit.create(KindergartenApi::class.java)
}