package site.yoonsang.findkindergarten.network

import okhttp3.Interceptor
import okhttp3.Response
import site.yoonsang.findkindergarten.BuildConfig

class HttpRequestInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        builder.addHeader("Authorization", BuildConfig.KAKAO_KEY)
        return chain.proceed(builder.build())
    }
}