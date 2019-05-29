package com.example.flicks.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.flicks.databinding.GenreListGridBinding
import com.example.flicks.models.Genre

class MovieGenreAdapter(private val onClickListener: OnClickListener) : ListAdapter<Genre, MovieGenreAdapter.GenreViewHolder>(
    DiffCallback
) {

    class GenreViewHolder(private var binding: GenreListGridBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(resultData: Genre) {
            binding.property = resultData
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Genre>() {
        override fun areItemsTheSame(oldItem: Genre, newItem: Genre): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Genre, newItem: Genre): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GenreViewHolder {
        return GenreViewHolder(
            GenreListGridBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        val resultData = getItem(position)
//        holder.itemView.setOnClickListener {
//            onClickListener.onClick(resultData)
//        }
        holder.bind(resultData)
    }
    class OnClickListener(val clickListener: (resultProperty: Genre) -> Unit){
        fun onClick(resultProperty: Genre) = clickListener(resultProperty)
    }
}