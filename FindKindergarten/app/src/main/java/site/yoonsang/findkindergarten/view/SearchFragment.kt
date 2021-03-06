package site.yoonsang.findkindergarten.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject
import site.yoonsang.findkindergarten.R
import site.yoonsang.findkindergarten.databinding.FragmentSearchBinding
import site.yoonsang.findkindergarten.model.KindergartenInfo
import site.yoonsang.findkindergarten.view.adapter.KindergartenLoadStateAdapter
import site.yoonsang.findkindergarten.view.adapter.KindergartenPagingAdapter
import site.yoonsang.findkindergarten.viewmodel.SearchViewModel
import java.io.BufferedReader
import java.io.InputStreamReader

@AndroidEntryPoint
class SearchFragment : Fragment(), KindergartenPagingAdapter.OnItemClickListener {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var sido = ""
        var sgg = ""
        var region = ""

        val sidoList = setSidoList()
        val sidoAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, sidoList)
        binding.searchSidoCodeSpinner.adapter = sidoAdapter

        binding.searchSidoCodeSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                sido = binding.searchSidoCodeSpinner.selectedItem.toString()
                region = convertRegion(sido)
                val sggList = setSggList(region)
                val sggAdapter =
                    ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        sggList
                    )
                binding.searchSggCodeSpinner.adapter = sggAdapter
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.d("checkkk", "onNothingSelected")
            }
        }

        binding.searchSggCodeSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    sgg = binding.searchSggCodeSpinner.selectedItem.toString()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    Log.d("checkkk", "onNothingSelected")
                }
            }

        val adapter = KindergartenPagingAdapter(this)
        binding.searchRecyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
            header = KindergartenLoadStateAdapter { adapter.retry() },
            footer = KindergartenLoadStateAdapter { adapter.retry() }
        )

        binding.searchSearchButton.setOnClickListener {
            viewModel.getKindergartenInfo(
                convertSidoCode(sido),
                convertSggCode(sgg, region)
            )
        }

        viewModel.kindergartenInfo.observe(viewLifecycleOwner) { data ->
            adapter.submitData(viewLifecycleOwner.lifecycle, data)
        }

        adapter.addLoadStateListener { loadState ->
            binding.searchProgressBar.isVisible = loadState.source.refresh is LoadState.Loading
            binding.searchRecyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
            binding.searchErrorText.isVisible = loadState.source.refresh is LoadState.Error
        }
    }

    override fun onItemClick(kindergartenInfo: KindergartenInfo) {
        findNavController().navigate(
            SearchFragmentDirections.actionSearchFragmentToDetailFragment(
                kindergartenInfo
            )
        )
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

    private fun convertSidoCode(sido: String): Int {
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
        return jsonObject.optInt(sido)
    }

    private fun convertSggCode(sgg: String, region: String): Int {
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
        return jsonObject.optInt(sgg)
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
            "???????????????" -> return "region_seoul"
            "???????????????" -> return "region_busan"
            "???????????????" -> return "region_daegu"
            "???????????????" -> return "region_incheon"
            "???????????????" -> return "region_gwangju"
            "???????????????" -> return "region_daejeon"
            "???????????????" -> return "region_ulsan"
            "?????????????????????" -> return "region_sejong"
            "?????????" -> return "region_gyeonggi"
            "?????????" -> return "region_gangwon"
            "????????????" -> return "region_chungbuk"
            "????????????" -> return "region_chungnam"
            "????????????" -> return "region_jeonbuk"
            "????????????" -> return "region_jeonnam"
            "????????????" -> return "region_gyeongbuk"
            "????????????" -> return "region_gyeongnam"
            "?????????????????????" -> return "region_jeju"
            else -> return "region_seoul"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}