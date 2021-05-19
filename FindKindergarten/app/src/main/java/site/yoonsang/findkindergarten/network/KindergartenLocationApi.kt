package site.yoonsang.findkindergarten.network

import retrofit2.http.GET
import retrofit2.http.Query
import site.yoonsang.findkindergarten.model.KindergartenLocationResponse

interface KindergartenLocationApi {

    companion object {
        const val BASE_URL = "https://dapi.kakao.com/"
    }

    @GET("/v2/local/search/keyword.json")
    suspend fun getKindergartenLocation(
        @Query("query") query: String,
        @Query("analyze_type") analyzeType: String? = "exact"
    ): KindergartenLocationResponse
}