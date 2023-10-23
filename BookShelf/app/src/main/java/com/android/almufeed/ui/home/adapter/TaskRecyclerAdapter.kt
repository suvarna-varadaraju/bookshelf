package com.android.almufeed.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.almufeed.business.domain.model.bookModel.BookInfo
import com.android.almufeed.databinding.RecyclerTaskadapterBinding
import com.android.almufeed.datasource.cache.models.book.BookEntity

class TaskRecyclerAdapter (val bookList: List<BookEntity>,private val listener: BookPagingAdapter.OnItemClickListener
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
        val currentItem = bookList.get(position)
        if (currentItem != null) {
            holder.bind(currentItem,position)
        }
    }

    inner class ItemViewHolder(private val binding: RecyclerTaskadapterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(currentItem: BookEntity, position: Int) {
            binding.apply {

            }
        }
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    interface OnItemClickListener {
        fun onItemClick(data: BookInfo)
    }
}