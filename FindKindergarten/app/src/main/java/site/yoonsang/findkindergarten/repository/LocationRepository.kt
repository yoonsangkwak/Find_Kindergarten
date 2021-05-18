package site.yoonsang.findkindergarten.repository

import site.yoonsang.findkindergarten.network.KindergartenLocationApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationRepository @Inject constructor(
    private val kindergartenLocationApi: KindergartenLocationApi
) {

    suspend fun getKindergartenLocation(query: String) =
        kindergartenLocationApi.getKindergartenLocation(query)
}