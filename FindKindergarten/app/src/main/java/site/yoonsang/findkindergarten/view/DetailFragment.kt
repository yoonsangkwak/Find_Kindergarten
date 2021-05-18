package site.yoonsang.findkindergarten.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import site.yoonsang.findkindergarten.R
import site.yoonsang.findkindergarten.databinding.FragmentDetailBinding
import site.yoonsang.findkindergarten.viewmodel.DetailViewModel

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<DetailViewModel>()

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

        val mapView = MapView(activity)
        binding.detailMapViewContainer.addView(mapView)
        viewModel.getKindergartenLocation().observe(viewLifecycleOwner) { data ->
            val x = data.documents[0].x
            val y = data.documents[0].y
            mapView.setMapCenterPoint(
                MapPoint.mapPointWithGeoCoord(
                    y.toDouble(), x.toDouble()
                ), true
            )
            mapView.setZoomLevel(1, true)
            mapView.zoomIn(true)
            mapView.zoomOut(true)
            val marker = MapPOIItem()
            marker.itemName = kindergartenInfo.kindername
            marker.markerType = MapPOIItem.MarkerType.RedPin
            marker.mapPoint = MapPoint.mapPointWithGeoCoord(y.toDouble(), x.toDouble())
            mapView.addPOIItem(marker)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}