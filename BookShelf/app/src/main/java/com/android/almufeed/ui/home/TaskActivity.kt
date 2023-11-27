package com.android.almufeed.ui.home

import android.R
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.almufeed.business.domain.state.DataState
import com.android.almufeed.business.domain.utils.exhaustive
import com.android.almufeed.databinding.ActivityTaskBinding
import com.android.almufeed.datasource.network.models.bookList.BookData
import com.android.almufeed.datasource.network.models.bookList.BookListNetworkResponse
import com.android.almufeed.ui.base.BaseViewModel
import com.android.almufeed.ui.home.adapter.TaskRecyclerAdapter
import com.android.almufeed.ui.login.LoginActivity
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_task.recyclerTask
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

@AndroidEntryPoint
class TaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTaskBinding
    private lateinit var taskRecyclerAdapter : TaskRecyclerAdapter
    private val homeViewModel: HomeViewModel by viewModels()
    private val baseViewModel: BaseViewModel by viewModels()
    private lateinit var pd : Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.aboutus.setText("Task List")
        binding.toolbar.incToolbarImage.visibility = View.VISIBLE

        binding.toolbar.incToolbarImage.setOnClickListener (View.OnClickListener { view ->
            this@TaskActivity.onBackPressedDispatcher.onBackPressed()
        })
        var resoureId = intent.getStringExtra("resourceId").toString()
        pd = Dialog(this, R.style.Theme_Black)
        val view: View = LayoutInflater.from(this).inflate(com.android.almufeed.R.layout.remove_border, null)
        pd.requestWindowFeature(Window.FEATURE_NO_TITLE)
        pd.getWindow()!!.setBackgroundDrawableResource(com.android.almufeed.R.color.transparent)
        pd.setContentView(view)
        pd.show()
        homeViewModel.requestForTask(resoureId)
        subscribeObservers()
    }

    private fun subscribeObservers() {
        homeViewModel.myTaskDataSTate.observe(this@TaskActivity) { dataState ->
            when (dataState) {
                is DataState.Error -> {
                    dataState.exception.message
                    if(dataState.exception.message.equals("Authentication failed")){
                        Toast.makeText(this@TaskActivity,dataState.exception.message, Toast.LENGTH_SHORT).show()
                        baseViewModel.setToken("")
                        Intent(this@TaskActivity, LoginActivity::class.java).apply {
                            startActivity(this)
                        }
                    }else{
                        Toast.makeText(this@TaskActivity,dataState.exception.message, Toast.LENGTH_SHORT).show()
                    }
                }
                is DataState.Loading -> {
                    if(!dataState.loading){
                        pd.dismiss()
                    } else {
                        pd.show()
                    }
                }
                is DataState.Success -> {
                    Log.e("AR_MYBUSS::", "Task Details: ${dataState.data}")
                    pd.dismiss()
                    binding.recyclerTask.apply {
                        taskRecyclerAdapter = TaskRecyclerAdapter(dataState.data,this@TaskActivity)
                        layoutManager = LinearLayoutManager(this@TaskActivity)
                        recyclerTask.adapter = taskRecyclerAdapter
                    }
                }
            }.exhaustive
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