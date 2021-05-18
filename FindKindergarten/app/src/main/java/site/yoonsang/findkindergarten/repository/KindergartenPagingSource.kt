package site.yoonsang.findkindergarten.repository

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import site.yoonsang.findkindergarten.BuildConfig
import site.yoonsang.findkindergarten.model.KindergartenInfo
import site.yoonsang.findkindergarten.network.KindergartenApi
import java.io.IOException

private const val SHOW_STARTING_PAGE_INDEX = 1
private const val ITEM_MEMBERS_IN_PAGE = 20

class KindergartenPagingSource(
    private val kindergartenApi: KindergartenApi,
    private val sidoCode: Int,
    private val sggCode: Int
): PagingSource<Int, KindergartenInfo>() {

    override fun getRefreshKey(state: PagingState<Int, KindergartenInfo>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, KindergartenInfo> {
        val position = params.key ?: SHOW_STARTING_PAGE_INDEX
        return try {
            val response = kindergartenApi.getKindergartenInfo(
                key = BuildConfig.API_KEY,
                pageCount = ITEM_MEMBERS_IN_PAGE,
                currentPage = position,
                sidoCode = sidoCode,
                sggCode = sggCode
            )
            val kinderInfo = response.kindergartenInfo

            LoadResult.Page(
                data = kinderInfo,
                prevKey = if (position == SHOW_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (kinderInfo.isEmpty()) null else position + 1
            )
        } catch (e: IOException) {
            Log.d("checkkk", "IO ${e.message}")
            LoadResult.Error(e)
        } catch (e: HttpException) {
            Log.d("checkkk", "Http ${e.message}")
            LoadResult.Error(e)
        }
    }
}