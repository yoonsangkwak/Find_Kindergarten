package site.yoonsang.findkindergarten.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import site.yoonsang.findkindergarten.databinding.LoadStateFooterBinding

class KindergartenLoadStateAdapter(private val retry: () -> Unit): LoadStateAdapter<KindergartenLoadStateAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: LoadStateFooterBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState: LoadState) {
            binding.loadStateProgressBar.isVisible = loadState is LoadState.Loading
            binding.loadStateErrorText.isVisible = loadState !is LoadState.Loading
            binding.loadStateRetryButton.isVisible = loadState !is LoadState.Loading
        }

        init {
            binding.loadStateRetryButton.setOnClickListener {
                retry.invoke()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        val binding = LoadStateFooterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }
}