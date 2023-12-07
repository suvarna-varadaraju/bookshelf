package com.android.almufeed.ui.home.instructionSet

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.almufeed.R
import com.android.almufeed.business.domain.state.DataState
import com.android.almufeed.business.domain.utils.exhaustive
import com.android.almufeed.databinding.ActivityCheckListBinding
import com.android.almufeed.ui.home.TaskDetailsActivity
import com.android.almufeed.ui.home.attachment.AddAttachmentActivity
import com.android.almufeed.ui.home.attachment.AttachmentList
import com.android.almufeed.ui.home.events.AddEventsActivity
import com.android.almufeed.ui.home.events.AddEventsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_task.recyclerTask
import kotlinx.android.synthetic.main.recycler_instructionadapter.view.checklist

@AndroidEntryPoint
class CheckListActivity : AppCompatActivity(),InstructionRecyclerAdapter.OnItemClickListener{
    private lateinit var binding: ActivityCheckListBinding
    private val checkListViewModel: CheckListViewModel by viewModels()
    private val addEventsViewModel: AddEventsViewModel by viewModels()
    private lateinit var instructionRecyclerAdapter: InstructionRecyclerAdapter
    private lateinit var taskId : String
    private lateinit var pd : Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = getIntent()
        taskId = intent.getStringExtra("taskid").toString()

        setSupportActionBar(binding.toolbar.incToolbarWithCenterLogoToolbar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.icon_actionbar_backbutton)
            actionBar.setDisplayHomeAsUpEnabled(true)
            binding.toolbar.aboutus.text = "Task : $taskId"
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }

        binding.toolbar.incToolbarEvent.visibility = View.VISIBLE
        binding.toolbar.incToolbarAttachment.visibility = View.VISIBLE
        binding.toolbar.incToolbarEvent.setOnClickListener (View.OnClickListener { view ->
            val intent = Intent(this@CheckListActivity, AddEventsActivity::class.java)
            intent.putExtra("taskid", taskId)
            startActivity(intent)
            finish()
        })

        binding.toolbar.incToolbarAttachment.setOnClickListener (View.OnClickListener { view ->
            val intent = Intent(this@CheckListActivity, AttachmentList::class.java)
            intent.putExtra("taskid", taskId)
            startActivity(intent)
            finish()
        })

        pd = Dialog(this, android.R.style.Theme_Black)
        val view: View = LayoutInflater.from(this).inflate(com.android.almufeed.R.layout.remove_border, null)
        pd.requestWindowFeature(Window.FEATURE_NO_TITLE)
        pd.getWindow()!!.setBackgroundDrawableResource(com.android.almufeed.R.color.transparent)
        pd.setContentView(view)
        pd.show()

        subscribeObservers()

        binding.btnAccept.setOnClickListener(View.OnClickListener { view ->
            val viewHolder = instructionRecyclerAdapter.createViewHolder(binding.recyclerTask, 0)
            instructionRecyclerAdapter.bindViewHolder(viewHolder, 0)
            pd = Dialog(this, android.R.style.Theme_Black)
            val view: View = LayoutInflater.from(this).inflate(R.layout.remove_border, null)
            pd.requestWindowFeature(Window.FEATURE_NO_TITLE)
            pd.getWindow()!!.setBackgroundDrawableResource(R.color.transparent)
            pd.setContentView(view)
            pd.show()

            for (position in 0 until instructionRecyclerAdapter.itemCount) {
                val viewHolder = instructionRecyclerAdapter.createViewHolder(binding.recyclerTask, position)
                instructionRecyclerAdapter.bindViewHolder(viewHolder, position)

                val btnYesInFirstItem = viewHolder.binding.checklist.btnYes
                val btnNoInFirstItem = viewHolder.binding.checklist.btnNo
                val btnfreeTextInFirstItem = viewHolder.binding.etMessage.text.toString()

                if(btnYesInFirstItem.isPressed || btnNoInFirstItem.isPressed || btnfreeTextInFirstItem.isNotEmpty()){

                    //addEventsViewModel.saveForEvent(taskId,"comments","Instruction set completed")
                }else{
                    pd.dismiss()
                    Toast.makeText(this@CheckListActivity,"All Instruction set are mandatory", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun subscribeObservers() {
        checkListViewModel.myTaskDataSTate.observe(this@CheckListActivity) { dataState ->
            when (dataState) {
                is DataState.Error -> {
                    pd.dismiss()
                    Toast.makeText(this@CheckListActivity,"Something went wrong", Toast.LENGTH_SHORT).show()
                }
                is DataState.Loading -> {

                }
                is DataState.Success -> {
                    Log.e("AR_MYBUSS::", "UI Details: ${dataState.data}")
                    pd.dismiss()
                    binding.recyclerTask.apply {
                        instructionRecyclerAdapter = InstructionRecyclerAdapter(dataState.data,this@CheckListActivity,this@CheckListActivity)
                        layoutManager = LinearLayoutManager(this@CheckListActivity)
                        recyclerTask.adapter = instructionRecyclerAdapter
                    }
                }

                else -> {}
            }.exhaustive
        }

        addEventsViewModel.mySetEventDataSTate.observe(this@CheckListActivity) { dataState ->
            when (dataState) {
                is DataState.Error -> {
                    pd.dismiss()
                    dataState.exception.message
                    Toast.makeText(this@CheckListActivity,dataState.exception.message, Toast.LENGTH_SHORT).show()
                }
                is DataState.Loading -> {

                }
                is DataState.Success -> {
                    Log.e("AR_MYBUSS::", "UI Details: ${dataState.data}")
                    pd.dismiss()
                    if(dataState.data.Success){
                        val intent = Intent(this@CheckListActivity, AddAttachmentActivity::class.java)
                        intent.putExtra("taskid", taskId)
                        startActivity(intent)
                        finish()
                    }else{
                        Toast.makeText(this@CheckListActivity,"please try later", Toast.LENGTH_SHORT).show()
                    }
                }
            }.exhaustive
        }
    }

    private fun subscribeObserversUpdate() {
        checkListViewModel.myUpdateDataSTate.observe(this@CheckListActivity) { dataState ->
            when (dataState) {
                is DataState.Error -> {
                    pd.dismiss()
                    Toast.makeText(this@CheckListActivity,"Some error, please try later", Toast.LENGTH_SHORT).show()
                }
                is DataState.Loading -> {

                }
                is DataState.Success -> {
                    pd.dismiss()
                    Log.e("AR_MYBUSS::", "update Success: ${dataState.data}")
                }

                else -> {}
            }.exhaustive
        }
    }

    override fun onResume() {
        super.onResume()
        checkListViewModel.requestForStep(taskId)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemClick(refId: Long, feedBackType: Int, answer: String) {
        pd.show()
        checkListViewModel.updateForStep(refId,feedBackType,answer,taskId)
        subscribeObserversUpdate()
    }
}