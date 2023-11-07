package com.android.almufeed.ui.home.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.almufeed.databinding.RecyclerTaskadapterBinding
import com.android.almufeed.datasource.network.models.bookList.BookData
import com.android.almufeed.datasource.network.models.bookList.BookListNetworkResponse
import com.android.almufeed.ui.home.RiskAssessment
import com.android.almufeed.ui.home.TaskDetailsActivity

class TaskRecyclerAdapter (val taskList: BookListNetworkResponse, val context: Context
) : RecyclerView.Adapter<TaskRecyclerAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = RecyclerTaskadapterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = taskList.task.get(position)
        if (currentItem != null) {
            holder.bind(currentItem,position)
        }
    }

    inner class ItemViewHolder(private val binding: RecyclerTaskadapterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(currentItem: BookData, position: Int) {
            binding.apply {
                itemView.setOnClickListener {
                    val intent = Intent(context, TaskDetailsActivity::class.java)
                    intent.putExtra("taskid", currentItem.id)
                    context.startActivity(intent)
                }

              /*  btnOpen.setOnClickListener {
                    val intent = Intent(context, TaskDetailsActivity::class.java)
                    intent.putExtra("taskid", currentItem.id)
                    context.startActivity(intent)
                }

                btnAccept.setOnClickListener {
                    val intent = Intent(context, RiskAssessment::class.java)
                    intent.putExtra("taskid", currentItem.id)
                    context.startActivity(intent)
                }*/
            }
        }
    }

    override fun getItemCount(): Int {
        return taskList.task.size
    }
}