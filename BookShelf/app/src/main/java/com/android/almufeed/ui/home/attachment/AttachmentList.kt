package com.android.almufeed.ui.home.attachment

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.almufeed.R
import com.android.almufeed.business.domain.state.DataState
import com.android.almufeed.business.domain.utils.exhaustive
import com.android.almufeed.databinding.ActivityAddAttachmentBinding
import com.android.almufeed.databinding.ActivityAttachmentListBinding
import com.android.almufeed.ui.base.BaseViewModel
import com.android.almufeed.ui.home.HomeViewModel
import com.android.almufeed.ui.home.adapter.TaskRecyclerAdapter
import com.android.almufeed.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_task.recyclerTask

@AndroidEntryPoint
class AttachmentList : AppCompatActivity() {
    private lateinit var binding: ActivityAttachmentListBinding
    private lateinit var taskId : String
    private lateinit var attachmentRecyclerAdapter : AttachmentRecyclerAdapter
    private val viewModel: GetAttachmentViewModel by viewModels()
    private lateinit var pd : Dialog
    private val baseViewModel: BaseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAttachmentListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = getIntent()
        taskId = intent.getStringExtra("taskid").toString()
        binding.toolbar.aboutus.setText("Task : " + taskId)
        binding.toolbar.incToolbarImage.visibility = View.VISIBLE

        binding.toolbar.incToolbarImage.setOnClickListener (View.OnClickListener { view ->
            this@AttachmentList.onBackPressedDispatcher.onBackPressed()
        })

        pd = Dialog(this, android.R.style.Theme_Black)
        val view: View = LayoutInflater.from(this).inflate(com.android.almufeed.R.layout.remove_border, null)
        pd.requestWindowFeature(Window.FEATURE_NO_TITLE)
        pd.getWindow()!!.setBackgroundDrawableResource(com.android.almufeed.R.color.transparent)
        pd.setContentView(view)
        pd.show()
        viewModel.requestForImage(taskId)
        subscribeObservers()
    }

    private fun subscribeObservers() {
        viewModel.myImageDataSTate.observe(this@AttachmentList) { dataState ->
            when (dataState) {
                is DataState.Error -> {
                    dataState.exception.message
                    if(dataState.exception.message.equals("Authentication failed")){
                        Toast.makeText(this@AttachmentList,dataState.exception.message, Toast.LENGTH_SHORT).show()
                        baseViewModel.setToken("")
                        Intent(this@AttachmentList, LoginActivity::class.java).apply {
                            startActivity(this)
                        }
                    }else{
                        Toast.makeText(this@AttachmentList,dataState.exception.message, Toast.LENGTH_SHORT).show()
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
                    binding.recyclerAttach.apply {
                        attachmentRecyclerAdapter = AttachmentRecyclerAdapter(dataState.data,this@AttachmentList)
                        layoutManager = LinearLayoutManager(this@AttachmentList)
                        binding.recyclerAttach.adapter = attachmentRecyclerAdapter
                    }
                }
            }.exhaustive
        }
    }

}