package com.example.flicks.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.flicks.databinding.HorizontalViewTrailerItemBinding

import com.example.flicks.models.MovieTrailerResult

class MovieTrailerAdapter(private val onClickListener: OnClickListener) : ListAdapter<MovieTrailerResult, MovieTrailerAdapter.TrailerViewHolder>(
    DiffCallback
) {

    class TrailerViewHolder(private var binding: HorizontalViewTrailerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(resultData: MovieTrailerResult) {
            binding.property = resultData
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<MovieTrailerResult>() {
        override fun areItemsTheSame(oldItem: MovieTrailerResult, newItem: MovieTrailerResult): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: MovieTrailerResult, newItem: MovieTrailerResult): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TrailerViewHolder {
        return TrailerViewHolder(
            HorizontalViewTrailerItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: TrailerViewHolder, position: Int) {
        val resultData = getItem(position)
//        holder.itemView.setOnClickListener {
//            onClickListener.onClick(resultData)
//        }
        holder.bind(resultData)
    }
    class OnClickListener(val clickListener: (resultProperty: MovieTrailerResult) -> Unit){
        fun onClick(resultProperty: MovieTrailerResult) = clickListener(resultProperty)
    }
}