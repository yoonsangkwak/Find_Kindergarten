package site.yoonsang.findkindergarten.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import site.yoonsang.findkindergarten.network.KindergartenApi
import site.yoonsang.findkindergarten.network.KindergartenLocationApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KindergartenRepository @Inject constructor(
    private val kindergartenApi: KindergartenApi,
//    private val kindergartenLocationApi: KindergartenLocationApi
    ) {

    fun getKindergartenInfo(sidoCode: Int, sggCode: Int) =
        Pager(
            config = PagingConfig(
                pageSize = 1,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                KindergartenPagingSource(kindergartenApi, sidoCode, sggCode)
            }
        ).liveData

//    fun getKindergartenLocation(query: String) =
//        kindergartenLocationApi.getKindergartenLocation(query)
}