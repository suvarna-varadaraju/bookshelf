package com.android.almufeed.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.almufeed.R
import com.android.almufeed.business.domain.model.bookModel.BookInfo
import com.android.almufeed.databinding.ActivityDashboardBinding
import com.android.almufeed.databinding.ActivityDocumentsBinding
import com.android.almufeed.databinding.ActivityTaskBinding
import com.android.almufeed.ui.home.adapter.BookPagingAdapter
import com.android.almufeed.ui.home.adapter.FavouriteRecyclerAdapter
import com.android.almufeed.ui.home.adapter.TaskRecyclerAdapter

class TaskActivity : AppCompatActivity() , TaskRecyclerAdapter.OnItemClickListener{
    private lateinit var binding: ActivityTaskBinding
    private lateinit var taskRecyclerAdapter : TaskRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.aboutus.setText("TASK LIST")
        taskRecyclerAdapter = TaskRecyclerAdapter(this,)
    }

    override fun onItemClick(data: BookInfo) {

    }
}