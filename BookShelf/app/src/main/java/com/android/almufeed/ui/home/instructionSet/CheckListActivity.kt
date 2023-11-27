package com.android.almufeed.ui.home.instructionSet

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
import com.android.almufeed.business.domain.state.DataState
import com.android.almufeed.business.domain.utils.exhaustive
import com.android.almufeed.databinding.ActivityCheckListBinding
import com.android.almufeed.ui.home.attachment.AddAttachmentActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_task.recyclerTask

@AndroidEntryPoint
class CheckListActivity : AppCompatActivity(),InstructionRecyclerAdapter.OnItemClickListener{
    private lateinit var binding: ActivityCheckListBinding
    private val checkListViewModel: CheckListViewModel by viewModels()
    private lateinit var instructionRecyclerAdapter: InstructionRecyclerAdapter
    private lateinit var taskId : String
    private lateinit var pd : Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = getIntent()
        taskId = intent.getStringExtra("taskid").toString()
        binding.toolbar.aboutus.setText("Task : " + taskId)
        binding.toolbar.incToolbarImage.visibility = View.VISIBLE

        binding.toolbar.incToolbarImage.setOnClickListener (View.OnClickListener { view ->
            this@CheckListActivity.onBackPressedDispatcher.onBackPressed()
        })

        pd = Dialog(this, android.R.style.Theme_Black)
        val view: View = LayoutInflater.from(this).inflate(com.android.almufeed.R.layout.remove_border, null)
        pd.requestWindowFeature(Window.FEATURE_NO_TITLE)
        pd.getWindow()!!.setBackgroundDrawableResource(com.android.almufeed.R.color.transparent)
        pd.setContentView(view)
        pd.show()

        checkListViewModel.requestForStep(taskId)
        subscribeObservers()

        binding.btnAccept.setOnClickListener(View.OnClickListener { view ->

            val intent = Intent(this@CheckListActivity, AddAttachmentActivity::class.java)
            intent.putExtra("taskid", taskId)
            startActivity(intent)
           /* val intent = Intent(this@CheckListActivity, TaskDetailsActivity::class.java)
            intent.putExtra("taskid", taskId)
            intent.putExtra("status", "Add Pictures")
            startActivity(intent)*/
        })
    }

    private fun subscribeObservers() {
        checkListViewModel.myTaskDataSTate.observe(this@CheckListActivity) { dataState ->
            when (dataState) {
                is DataState.Error -> {
                    Toast.makeText(this@CheckListActivity,"Something went wrong", Toast.LENGTH_SHORT).show()
                }
                is DataState.Loading -> {

                }
                is DataState.Success -> {
                    Log.e("AR_MYBUSS::", "UI Details: ${dataState.data}")
                    pd.dismiss()
                    binding.recyclerTask?.apply {
                        instructionRecyclerAdapter = InstructionRecyclerAdapter(dataState.data,this@CheckListActivity,this@CheckListActivity)
                        layoutManager = LinearLayoutManager(this@CheckListActivity)
                        recyclerTask.adapter = instructionRecyclerAdapter
                    }
                }
            }.exhaustive
        }
    }

    private fun subscribeObserversUpdate() {
        checkListViewModel.myUpdateDataSTate.observe(this@CheckListActivity) { dataState ->
            when (dataState) {
                is DataState.Error -> {
                    Toast.makeText(this@CheckListActivity,"Something went wrong", Toast.LENGTH_SHORT).show()
                }
                is DataState.Loading -> {

                }
                is DataState.Success -> {
                    pd.dismiss()
                    Log.e("AR_MYBUSS::", "update Success: ${dataState.data}")
                }
            }.exhaustive
        }
    }

    override fun onItemClick(refId: Long, feedBackType: Int, answer: String) {
        pd.show()
        checkListViewModel.updateForStep(refId,feedBackType,answer,taskId)
        subscribeObserversUpdate()
    }
}