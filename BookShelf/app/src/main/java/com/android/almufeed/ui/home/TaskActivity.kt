package com.android.almufeed.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.almufeed.databinding.ActivityTaskBinding
import com.android.almufeed.datasource.network.models.bookList.BookData
import com.android.almufeed.datasource.network.models.bookList.BookListNetworkResponse
import com.android.almufeed.ui.home.adapter.TaskRecyclerAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_task.recyclerTask

class TaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTaskBinding
    private lateinit var taskRecyclerAdapter : TaskRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.aboutus.setText("Task List")
        binding.toolbar.incToolbarImage.visibility = View.VISIBLE

        binding.toolbar.incToolbarImage.setOnClickListener (View.OnClickListener { view ->
            this@TaskActivity.onBackPressedDispatcher.onBackPressed()
        })

        var taskList: BookListNetworkResponse = getTaskList(this@TaskActivity)
        binding.recyclerTask.apply {
            taskRecyclerAdapter = TaskRecyclerAdapter(taskList,this@TaskActivity)
            layoutManager = LinearLayoutManager(this@TaskActivity)
            recyclerTask.adapter = taskRecyclerAdapter
        }
    }

    fun getTaskList(context: Context) : BookListNetworkResponse{
        lateinit var jsonString: String
        try {
            jsonString = context.assets.open("tasklist.json")
                .bufferedReader()
                .use { it.readText() }
        } catch (ioException: Exception) {
            ioException.stackTrace
        }
        val listTaskType = object : TypeToken<BookListNetworkResponse>() {}.type
        return Gson().fromJson(jsonString, listTaskType)
    }
}