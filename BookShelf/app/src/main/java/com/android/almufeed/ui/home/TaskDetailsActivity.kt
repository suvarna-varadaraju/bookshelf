package com.android.almufeed.ui.home

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.almufeed.R
import com.android.almufeed.business.domain.state.DataState
import com.android.almufeed.business.domain.utils.dateFormater
import com.android.almufeed.business.domain.utils.exhaustive
import com.android.almufeed.business.domain.utils.toast
import com.android.almufeed.databinding.ActivityTaskDetailsBinding
import com.android.almufeed.ui.base.BaseInterface
import com.android.almufeed.ui.base.BaseViewModel
import com.android.almufeed.ui.home.adapter.TaskRecyclerAdapter
import com.android.almufeed.ui.home.attachment.AddAttachmentActivity
import com.android.almufeed.ui.home.attachment.AttachmentList
import com.android.almufeed.ui.home.events.AddEventsActivity
import com.android.almufeed.ui.home.events.AddEventsViewModel
import com.android.almufeed.ui.home.instructionSet.CheckListActivity
import com.android.almufeed.ui.home.rateus.RatingActivity
import com.android.almufeed.ui.login.LoginActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_task.recyclerTask

@Suppress("IMPLICIT_CAST_TO_ANY")
@AndroidEntryPoint
class TaskDetailsActivity : AppCompatActivity(), BaseInterface {
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var binding: ActivityTaskDetailsBinding
    private val addEventsViewModel: AddEventsViewModel by viewModels()
    private lateinit var pd : Dialog
    private lateinit var taskId : String
    private val baseViewModel: BaseViewModel by viewModels()
    private lateinit var snack: Snackbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = getIntent()
        taskId = intent.getStringExtra("taskid").toString()

        setSupportActionBar(binding.toolbar.incToolbarWithCenterLogoToolbar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.icon_actionbar_backbutton)
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = "Task : $taskId"
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }

        binding.toolbar.linTool.visibility = View.VISIBLE
        baseViewModel.isNetworkConnected.observe(this) { isNetworkAvailable ->
            showNetworkSnackBar(isNetworkAvailable)
        }

        binding.apply {
            snack = Snackbar.make(
                root,
                getString(com.android.almufeed.R.string.text_network_not_available),
                Snackbar.LENGTH_INDEFINITE
            )
        }

        subscribeObservers("")

        binding.toolbar.incToolbarEvent.setOnClickListener (View.OnClickListener {
            val intent = Intent(this@TaskDetailsActivity, AddEventsActivity::class.java)
            intent.putExtra("taskid", taskId)
            startActivity(intent)
        })

        binding.toolbar.incToolbarAttachment.setOnClickListener (View.OnClickListener {
            val intent = Intent(this@TaskDetailsActivity, AttachmentList::class.java)
            intent.putExtra("taskid", taskId)
            startActivity(intent)
        })

        binding.btnAccept.setOnClickListener (View.OnClickListener { view ->
            if(binding.btnAccept.text.toString().equals("Accept")){
                addEventsViewModel.saveForEvent(taskId,"comments","Accepted")
                subscribeObservers("Accept")
            }else if(binding.btnAccept.text.toString().equals("Start")){
                addEventsViewModel.saveForEvent(taskId,"comments","Started")
                subscribeObservers("Start")
            }else if(binding.btnAccept.text.toString().equals("Continue")){
                val intent = Intent(this@TaskDetailsActivity, CheckListActivity::class.java)
                intent.putExtra("taskid", taskId)
                startActivity(intent)
                finish()
            }else if(binding.btnAccept.text.toString().equals("Add Pictures")){
                val intent = Intent(this@TaskDetailsActivity, AddAttachmentActivity::class.java)
                intent.putExtra("taskid", taskId)
                startActivity(intent)
                finish()
            }else if(binding.btnAccept.text.toString().equals("Complete Task")){
                val intent = Intent(this@TaskDetailsActivity, RatingActivity::class.java)
                intent.putExtra("taskid", taskId)
                startActivity(intent)
                finish()
            }else if(binding.btnAccept.text.toString().equals("Start Risk Assessment")){
                val intent = Intent(this@TaskDetailsActivity, RiskAssessment::class.java)
                intent.putExtra("taskid", taskId)
                startActivity(intent)
                finish()
            }
        })
    }

    private fun subscribeObservers(event: String) {

        addEventsViewModel.mySetEventDataSTate.observe(this@TaskDetailsActivity) { dataState ->
            when (dataState) {
                is DataState.Error -> {
                    dataState.exception.message
                    if(dataState.exception.message.equals("Authentication failed")){
                        Toast.makeText(this@TaskDetailsActivity,dataState.exception.message, Toast.LENGTH_SHORT).show()
                        baseViewModel.setToken("")
                        baseViewModel.updateUsername("")
                        Intent(this@TaskDetailsActivity, LoginActivity::class.java).apply {
                            startActivity(this)
                            finish()
                        }
                    }else{
                        Toast.makeText(this@TaskDetailsActivity,dataState.exception.message, Toast.LENGTH_SHORT).show()
                    }
                }
                is DataState.Loading -> {

                }
                is DataState.Success -> {
                    Log.e("AR_MYBUSS::", "UI Details: ${dataState.data}")
                    if(dataState.data.Success){
                        if(event.equals("Accept")){
                            val intent = Intent(this@TaskDetailsActivity, RiskAssessment::class.java)
                            intent.putExtra("taskid", taskId)
                            startActivity(intent)
                            finish()
                        }else if(event.equals("Start")){
                            val intent = Intent(this@TaskDetailsActivity, CheckListActivity::class.java)
                            intent.putExtra("taskid", taskId)
                            startActivity(intent)
                            finish()
                        }else{

                        }
                    }else{
                        Toast.makeText(this@TaskDetailsActivity,"Some error, please try later", Toast.LENGTH_SHORT).show()
                    }
                }
            }.exhaustive
        }

        homeViewModel.myTaskDataSTate.observe(this@TaskDetailsActivity) { dataState ->
            when (dataState) {
                is DataState.Error -> {
                    dataState.exception.message
                    if(dataState.exception.message.equals("Authentication failed")){
                        Toast.makeText(this@TaskDetailsActivity,dataState.exception.message, Toast.LENGTH_SHORT).show()
                        baseViewModel.setToken("")
                        baseViewModel.updateUsername("")
                        Intent(this@TaskDetailsActivity, LoginActivity::class.java).apply {
                            startActivity(this)
                            finish()
                        }
                    }else{
                        Toast.makeText(this@TaskDetailsActivity,dataState.exception.message, Toast.LENGTH_SHORT).show()
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
                    if(dataState.data.task.isEmpty()){
                        val intent = Intent(this@TaskDetailsActivity, TaskActivity::class.java)
                        startActivity(intent)
                        finish()
                    }else{
                        for (i in dataState.data.task.indices) {
                            if(taskId == dataState.data.task[i].TaskId.toString()){
                                binding.apply {
                                    toolbar.aboutus.text = "Task :  $taskId"
                                    txtTaskid.text = "Task Id : $taskId"
                                    txtShortNote?.text = "Task Description : ${dataState.data.task[i].Notes}"
                                    txtpro?.text = "Priority : ${dataState.data.task[i].Priority}"
                                    txtsceduleDate?.text = "Reported Date : " + dateFormater(dataState.data.task[i].scheduledDate)
                                    txtContactname?.text = "Name : ${dataState.data.task[i].CustName}"
                                    txtNumber?.text = "Mobile Number : ${dataState.data.task[i].Phone}"
                                    txtBuilding?.text = "Building : ${dataState.data.task[i].Building}"
                                    txtLocation?.text = "Location : ${dataState.data.task[i].Location}"
                                    txtDate?.text = "Due Date :  " + dateFormater(dataState.data.task[i].attendDate)

                                    if(dataState.data.task[i].LOC.equals("Risk Assessment Completed")){
                                        txtAction?.text = dataState.data.task[i].LOC
                                        btnAccept.text = "Start"
                                    }else if(dataState.data.task[i].LOC.equals("Accepted")){
                                        txtAction?.text = dataState.data.task[i].LOC
                                        btnAccept.text = "Start Risk Assessment"
                                    }else if(dataState.data.task[i].LOC.equals("Instruction set completed")){
                                        txtAction?.text = dataState.data.task[i].LOC
                                        btnAccept.text = "Add Pictures"
                                    }else if(dataState.data.task[i].LOC.equals("Started")){
                                        txtAction?.text = dataState.data.task[i].LOC
                                        btnAccept.text = "Continue"
                                    }else if(dataState.data.task[i].LOC.equals("Before Task")){
                                        txtAction?.text = "Before Task Pictures Added"
                                        btnAccept.text = "Add Pictures"
                                    }else if(dataState.data.task[i].LOC.equals("After Task")){
                                        txtAction?.text = "After Task Pictures Added"
                                        btnAccept.text = "Complete Task"
                                    }else{
                                        txtAction?.text = dataState.data.task[i].LOC
                                    }
                                }
                            }
                        }
                    }
                }
            }.exhaustive
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showNetworkSnackBar(isNetworkAvailable: Boolean) {
        if (isNetworkAvailable) {
            if (snack.isShown) {
                this.toast(getString(com.android.almufeed.R.string.text_network_connected))
            }
        } else {
            snack.show()
        }
    }

    override fun onResume() {
        super.onResume()
        pd = Dialog(this, android.R.style.Theme_Black)
        val view: View = LayoutInflater.from(this).inflate(R.layout.remove_border, null)
        pd.requestWindowFeature(Window.FEATURE_NO_TITLE)
        pd.window!!.setBackgroundDrawableResource(R.color.transparent)
        pd.setContentView(view)
        pd.show()
        homeViewModel.requestForTask()
    }
}