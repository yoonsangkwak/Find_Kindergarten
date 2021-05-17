package site.yoonsang.findkindergarten.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import site.yoonsang.findkindergarten.databinding.ItemKindergartenBinding
import site.yoonsang.findkindergarten.model.KindergartenInfo

class KindergartenPagingAdapter() :
    PagingDataAdapter<KindergartenInfo, KindergartenPagingAdapter.ViewHolder>(ITEM_COMPARATOR) {

    companion object {
        private val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<KindergartenInfo>() {
            override fun areItemsTheSame(
                oldItem: KindergartenInfo,
                newItem: KindergartenInfo
            ): Boolean {
                return oldItem.kindercode == newItem.kindercode
            }

            override fun areContentsTheSame(
                oldItem: KindergartenInfo,
                newItem: KindergartenInfo
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class ViewHolder(private val binding: ItemKindergartenBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(kindergartenInfo: KindergartenInfo) {
            binding.kindergartenInfo = kindergartenInfo
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemKindergartenBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }
}