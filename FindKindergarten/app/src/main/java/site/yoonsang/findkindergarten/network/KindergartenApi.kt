package site.yoonsang.findkindergarten.network

import retrofit2.http.GET
import retrofit2.http.Query
import site.yoonsang.findkindergarten.model.KindergartenResponse

interface KindergartenApi {

    companion object {
        const val BASE_URL = "https://e-childschoolinfo.moe.go.kr/"
    }

    @GET("/api/notice/basicInfo.do")
    suspend fun getKindergartenInfo(
        @Query("key") key: String,
        @Query("pageCnt") pageCount: Int? = null,
        @Query("currentPage") currentPage: Int? = null,
        @Query("sidoCode") sidoCode: Int,
        @Query("sggCode") sggCode: Int,
    ): KindergartenResponse
}