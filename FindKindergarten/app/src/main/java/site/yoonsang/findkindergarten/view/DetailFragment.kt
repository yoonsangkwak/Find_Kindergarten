package site.yoonsang.findkindergarten.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import site.yoonsang.findkindergarten.R
import site.yoonsang.findkindergarten.databinding.FragmentDetailBinding

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}