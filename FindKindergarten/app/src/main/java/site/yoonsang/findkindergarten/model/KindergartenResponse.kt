package site.yoonsang.findkindergarten.model


import com.google.gson.annotations.SerializedName

data class KindergartenResponse(
    @SerializedName("kinderInfo")
    val kindergartenInfo: List<KindergartenInfo>,
    @SerializedName("status")
    val status: String
)