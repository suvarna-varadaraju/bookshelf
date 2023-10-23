package com.android.almufeed.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.almufeed.business.domain.model.bookModel.BookInfo
import com.android.almufeed.databinding.ListItemViewBookListingsBinding
import com.bumptech.glide.Glide

class BookPagingAdapter (private val listener: OnItemClickListener,context: Context
) : PagingDataAdapter<BookInfo, BookPagingAdapter.TopUpItemViewHolder>(TOP_UP_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopUpItemViewHolder {
        val binding =
            ListItemViewBookListingsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TopUpItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TopUpItemViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem,position)
            holder.itemView.setOnClickListener {
                listener.onItemClick(currentItem)
            }
        }
    }

    inner class TopUpItemViewHolder(private val binding: ListItemViewBookListingsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(currentItem: BookInfo,position: Int) {
            binding.apply {
                Glide.with(binding.root.context)
                    .load(currentItem.volumeInfo.imageLinks?.thumbnail)
                    .into(binding.catalogueImage)
                binding.textName.text = currentItem.volumeInfo.title
                binding.textAuthor.text = "Author : ${currentItem.volumeInfo.authors?.get(0)}"
                binding.btnRating.text = currentItem.volumeInfo.averageRating.toString()
            }
        }
    }



    companion object {
        private val TOP_UP_COMPARATOR = object : DiffUtil.ItemCallback<BookInfo>() {
            override fun areItemsTheSame(oldItem: BookInfo, newItem: BookInfo) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: BookInfo, newItem: BookInfo) =
                oldItem == newItem
        }
    }

    interface OnItemClickListener {
        fun onItemClick(data: BookInfo)
    }
}