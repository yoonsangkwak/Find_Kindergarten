package site.yoonsang.findkindergarten.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class KindergartenInfo(
    val addr: String?,
    val clcnt3: String?,
    val clcnt4: String?,
    val clcnt5: String?,
    val edate: String?,
    val establish: String?,
    val hpaddr: String?,
    val key: String?,
    val kindercode: String?,
    val kindername: String?,
    val ldgrname: String?,
    val mixclcnt: String?,
    val mixppcnt: String?,
    val odate: String?,
    val officeedu: String?,
    val opertime: String?,
    val pbnttmng: String?,
    val ppcnt3: String?,
    val ppcnt4: String?,
    val ppcnt5: String?,
    val rppnname: String?,
    val subofficeedu: String?,
    val telno: String?,
    val shclcnt: String?,
    val shppcnt: String?
) : Parcelable