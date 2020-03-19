package com.example.taskati.features.login.home.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.example.taskati.R
import com.example.taskati.common.data.db.TaskTable
import kotlinx.android.synthetic.main.task_layout.view.*

class TaskAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TaskTable>() {

        override fun areItemsTheSame(oldItem: TaskTable, newItem: TaskTable) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: TaskTable, newItem: TaskTable) =
            oldItem.id == newItem.id

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return TaskViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.task_layout,
                parent,
                false
            ),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TaskViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<TaskTable>) {
        differ.submitList(list)
    }

    class TaskViewHolder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: TaskTable) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }
            itemView.tv_title_task.text = item.title
            itemView.tv_date_task.text = item.date

        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: TaskTable)
    }
}

