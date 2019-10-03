package com.my.flicks.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.my.flicks.databinding.GridViewItemBinding
import com.my.flicks.models.Result

class PhotoGridAdapter(private val onClickListener: OnClickListener) : PagedListAdapter<Result, PhotoGridAdapter.ResultViewHolder>(DiffCallback) {

    class ResultViewHolder(private var binding: GridViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(resultData: Result) {
            binding.property = resultData
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ResultViewHolder {
        return ResultViewHolder(GridViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        val resultData = getItem(position)
        holder.itemView.setOnClickListener {
            resultData?.let { it1 -> onClickListener.onClick(it1) }
        }
        resultData?.let { holder.bind(it) }
    }
    class OnClickListener(val clickListener: (resultProperty: Result) -> Unit){
        fun onClick(resultProperty: Result) = clickListener(resultProperty)
    }
}