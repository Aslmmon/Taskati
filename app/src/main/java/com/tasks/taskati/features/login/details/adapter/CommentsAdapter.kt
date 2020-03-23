package com.tasks.taskati.features.login.details.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.tasks.taskati.R
import com.tasks.taskati.common.data.db.comments_table.CommentsTable
import kotlinx.android.synthetic.main.comment_layout.view.*

class CommentsAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CommentsTable>() {

        override fun areItemsTheSame(oldItem: CommentsTable, newItem: CommentsTable) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: CommentsTable, newItem: CommentsTable) =
            oldItem.id == newItem.id

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return CommentViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.comment_layout,
                parent,
                false
            ),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CommentViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<CommentsTable>) {
        differ.submitList(list)
    }

    class CommentViewHolder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun bind(item: CommentsTable) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }
            itemView.tv_comment.text = "\u25CF ${item.comments}"

        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: CommentsTable)
    }
}

