package com.my.flicks.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.my.flicks.databinding.CommentsListBinding
import com.my.flicks.models.Comment

class MovieCommentAdapter(private val onClickListener: OnClickListener) : ListAdapter<Comment, MovieCommentAdapter.CommentsViewHolder>(
    DiffCallback
) {

    class CommentsViewHolder(private var binding: CommentsListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(resultData: Comment) {
            binding.property = resultData
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Comment>() {
        override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CommentsViewHolder {
        return CommentsViewHolder(
            CommentsListBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
        val resultData = getItem(position)
//        holder.itemView.setOnClickListener {
//            onClickListener.onClick(resultData)
//        }
        holder.bind(resultData)
    }
    class OnClickListener(val clickListener: (resultProperty: Comment) -> Unit){
        fun onClick(resultProperty: Comment) = clickListener(resultProperty)
    }
}