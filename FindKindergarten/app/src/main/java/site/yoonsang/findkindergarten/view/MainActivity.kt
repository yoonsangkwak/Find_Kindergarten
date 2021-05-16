package site.yoonsang.findkindergarten.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import org.json.JSONObject
import site.yoonsang.findkindergarten.R
import site.yoonsang.findkindergarten.databinding.ActivityMainBinding
import java.io.BufferedReader
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val sidoList = setSidoList()
        val sidoAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, sidoList)
        binding.mainSidoCodeSpinner.adapter = sidoAdapter

        binding.mainSidoCodeSpinner.onItemSelectedListener = (object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val sido = binding.mainSidoCodeSpinner.selectedItem.toString()
                val region = convertRegion(sido)
                val sggList = setSggList(region)
                val sggAdapter =
                    ArrayAdapter(
                        this@MainActivity,
                        android.R.layout.simple_spinner_dropdown_item,
                        sggList
                    )
                binding.mainSggCodeSpinner.adapter = sggAdapter
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.d("checkkk", "onNothingSelected")
            }
        })
    }

    private fun setSidoList(): ArrayList<String> {
        val assetManager = resources.assets
        val inputStream = assetManager.open("sido.json")
        val inputStreamReader = InputStreamReader(inputStream)
        val reader = BufferedReader(inputStreamReader)

        val buffer = StringBuffer()
        var line = reader.readLine()
        while (line != null) {
            buffer.append(line + "\n")
            line = reader.readLine()
        }
        val jsonData = buffer.toString()
        val jsonObject = JSONObject(jsonData)

        val sidoList = arrayListOf<String>()
        for (sido in jsonObject.keys()) {
            sidoList.add(sido)
        }
        return sidoList
    }

    private fun setSggList(region: String): ArrayList<String> {
        val assetManager = resources.assets
        val inputStream = assetManager.open("${region}.json")
        val inputStreamReader = InputStreamReader(inputStream)
        val reader = BufferedReader(inputStreamReader)

        val buffer = StringBuffer()
        var line = reader.readLine()
        while (line != null) {
            buffer.append(line + "\n")
            line = reader.readLine()
        }
        val jsonData = buffer.toString()
        val jsonObject = JSONObject(jsonData)

        val sggList = arrayListOf<String>()
        for (sgg in jsonObject.keys()) {
            sggList.add(sgg)
        }
        return sggList
    }

    private fun convertRegion(sido: String): String {
        when (sido) {
            "서울특별시" -> return "region_seoul"
            "부산광역시" -> return "region_busan"
            "대구광역시" -> return "region_daegu"
            "인천광역시" -> return "region_incheon"
            "광주광역시" -> return "region_gwangju"
            "대전광역시" -> return "region_daejeon"
            "울산광역시" -> return "region_ulsan"
            "세종특별자치시" -> return "region_sejong"
            "경기도" -> return "region_gyeonggi"
            "강원도" -> return "region_gangwon"
            "충청북도" -> return "region_chungbuk"
            "충청남도" -> return "region_chungnam"
            "전라북도" -> return "region_jeonbuk"
            "전라남도" -> return "region_jeonnam"
            "경상북도" -> return "region_gyeongbuk"
            "경상남도" -> return "region_gyeongnam"
            "제주특별자치도" -> return "region_jeju"
            else -> return "region_seoul"
        }
    }
}