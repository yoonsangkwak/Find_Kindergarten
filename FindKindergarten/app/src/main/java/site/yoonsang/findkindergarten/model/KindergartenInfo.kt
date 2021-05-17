package site.yoonsang.findkindergarten.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class KindergartenInfo(
    @SerializedName("addr")
    val addr: String,
    @SerializedName("clcnt3")
    val clcnt3: String?,
    @SerializedName("clcnt4")
    val clcnt4: String?,
    @SerializedName("clcnt5")
    val clcnt5: String?,
    @SerializedName("edate")
    val edate: String,
    @SerializedName("establish")
    val establish: String,
    @SerializedName("hpaddr")
    val hpaddr: String?,
    @SerializedName("key")
    val key: String,
    @SerializedName("kindercode")
    val kindercode: String,
    @SerializedName("kindername")
    val kindername: String,
    @SerializedName("ldgrname")
    val ldgrname: String,
    @SerializedName("mixclcnt")
    val mixclcnt: String,
    @SerializedName("mixppcnt")
    val mixppcnt: String,
    @SerializedName("odate")
    val odate: String,
    @SerializedName("officeedu")
    val officeedu: String,
    @SerializedName("opertime")
    val opertime: String,
    @SerializedName("pbnttmng")
    val pbnttmng: String,
    @SerializedName("ppcnt3")
    val ppcnt3: String?,
    @SerializedName("ppcnt4")
    val ppcnt4: String?,
    @SerializedName("ppcnt5")
    val ppcnt5: String?,
    @SerializedName("rppnname")
    val rppnname: String,
    @SerializedName("subofficeedu")
    val subofficeedu: String,
    @SerializedName("telno")
    val telno: String
): Parcelable