package site.yoonsang.findkindergarten.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import site.yoonsang.findkindergarten.R
import site.yoonsang.findkindergarten.databinding.FragmentDetailBinding
import site.yoonsang.findkindergarten.model.KindergartenInfo
import site.yoonsang.findkindergarten.viewmodel.DetailViewModel

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<DetailViewModel>()
    private lateinit var mapView: MapView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = DetailFragmentArgs.fromBundle(requireArguments())
        val kindergartenInfo = args.kindergartenInfo

        val noInfo = "해당 정보 없음"

        binding.kindergartenInfo = kindergartenInfo
        binding.noInfo = noInfo

        viewModel.query.value = kindergartenInfo.addr

        if (kindergartenInfo.hpaddr != null) {
            binding.detailHompageLink.setTextColor(requireContext().getColor(R.color.link))
        } else {
            binding.detailHompageLink.setTextColor(requireContext().getColor(R.color.black))
        }

        if (kindergartenInfo.telno != null) {
            binding.detailTelNumber.setTextColor(requireContext().getColor(R.color.link))
        } else {
            binding.detailTelNumber.setTextColor(requireContext().getColor(R.color.black))
        }

        binding.detailHompageLink.setOnClickListener {
            if (kindergartenInfo.hpaddr != null) {
                AlertDialog.Builder(requireContext())
                    .setMessage("홈페이지로 이동하시겠습니까?")
                    .setPositiveButton("확인") { _, _ ->
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(kindergartenInfo.hpaddr))
                        startActivity(intent)
                    }
                    .setNegativeButton("취소", null)
                    .create()
                    .show()
            }
        }

        binding.detailTelNumber.setOnClickListener {
            if (kindergartenInfo.telno != null) {
                AlertDialog.Builder(requireContext())
                    .setMessage("전화 연결하시겠습니까?")
                    .setPositiveButton("확인") { _, _ ->
                        val phoneNumber = kindergartenInfo.telno!!.replace("-".toRegex(), "")
                        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
                        startActivity(intent)
                    }
                    .setNegativeButton("취소", null)
                    .create()
                    .show()
            }
        }

        mapView = MapView(activity)
        binding.detailMapViewContainer.addView(mapView)
        viewModel.getKindergartenLocation().observe(viewLifecycleOwner) { data ->
            data.documents.forEach {
                if (it.placeName == kindergartenInfo.kindername || it.placeName.contains("유치원")) {
                    val x = it.x.toDouble()
                    val y = it.y.toDouble()
                    getKindergartenLocation(kindergartenInfo, x, y)
                    return@forEach
                }
            }
        }
    }

    private fun getKindergartenLocation(kindergartenInfo: KindergartenInfo, x: Double, y: Double) {
        mapView.setMapCenterPoint(
            MapPoint.mapPointWithGeoCoord(
                y, x
            ), true
        )
        mapView.setZoomLevel(0, true)
        mapView.zoomIn(true)
        mapView.zoomOut(true)
        val marker = MapPOIItem()
        marker.itemName = kindergartenInfo.kindername
        marker.markerType = MapPOIItem.MarkerType.RedPin
        marker.mapPoint = MapPoint.mapPointWithGeoCoord(y, x)
        mapView.addPOIItem(marker)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}