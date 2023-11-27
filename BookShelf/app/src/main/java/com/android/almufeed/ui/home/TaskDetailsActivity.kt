package com.android.almufeed.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.android.almufeed.R
import com.android.almufeed.databinding.ActivityTaskDetailsBinding
import com.android.almufeed.ui.home.instructionSet.CheckListActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaskDetailsActivity : AppCompatActivity(){
    lateinit var backPress: ImageView
    lateinit var aboutus: TextView
    lateinit var currentStatus: TextView
    lateinit var btnAccept: Button
    private lateinit var binding: ActivityTaskDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_details)
        binding = ActivityTaskDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        backPress = findViewById<ImageView>(R.id.inc_toolbar_image)
        aboutus = findViewById<TextView>(R.id.aboutus)
        btnAccept = findViewById<Button>(R.id.btn_accept)
        currentStatus = findViewById<TextView>(R.id.txt_action)
        binding.toolbar.incToolbarImage.visibility= View.VISIBLE
        binding.toolbar.incToolbarImage.setOnClickListener (View.OnClickListener { view ->
            this@TaskDetailsActivity.onBackPressedDispatcher.onBackPressed()
        })


        val intent = getIntent()
        val taskId = intent.getStringExtra("taskid")
        val note = intent.getStringExtra("description")
        val priority = intent.getStringExtra("priority")
        val scheduleDate = intent.getStringExtra("scheduledate")
        val dueDate = intent.getStringExtra("duedate")
        val cusName = intent.getStringExtra("cusname")
        val mobile = intent.getStringExtra("mobile")
        val building = intent.getStringExtra("building")
        val location = intent.getStringExtra("location")
        val status = intent.getStringExtra("status")

        // set all text

        binding.apply {
            toolbar.aboutus.text = "Task :  $taskId"
            txtTaskid.text = "Task Id : $taskId"
            txtShortNote?.text = "Task Description : $note"
            txtpro?.text = "Priority : $priority"
            txtsceduleDate?.text = "Schedule Date : $scheduleDate"
            txtContactname?.text = "Name : $cusName"
            txtNumber?.text = "Mobile Number : $mobile"
            txtbuilding?.text = "Building : $building"
            txtlocation?.text = "Location : $location"
            txtDate?.text = "Due Date : $dueDate"
            txtAction?.text = status
        }
        /*if(currentAvailableStatus == "Risk Assessment Completed"){
            currentStatus.setText("Risk Assessment Completed")
            currentStatus.setTextColor(resources.getColor(R.color.green))
            btnAccept.setBackgroundColor(resources.getColor(R.color.primary))
            btnAccept.setText("Proof Of Attendence")
        }else if(currentAvailableStatus == "Add Instruction Steps"){
            currentStatus.setText("Started")
            currentStatus.setTextColor(resources.getColor(R.color.green))
            btnAccept.setBackgroundColor(resources.getColor(R.color.primary))
            btnAccept.setText("Add Instruction Steps")
        }else if(currentAvailableStatus == "Add Pictures"){
            currentStatus.setText("Started")
            currentStatus.setTextColor(resources.getColor(R.color.green))
            btnAccept.setBackgroundColor(resources.getColor(R.color.primary))
            btnAccept.setText("Add Pictures")
        }*/

        btnAccept.setOnClickListener (View.OnClickListener { view ->
            val intent = Intent(this@TaskDetailsActivity, CheckListActivity::class.java)
            intent.putExtra("taskid", taskId)
            startActivity(intent)

           /* if(btnAccept.text == "Accept"){
                currentStatus.setText("Accepted")
                currentStatus.setTextColor(resources.getColor(R.color.green))
                btnAccept.setBackgroundColor(resources.getColor(R.color.primary))
                btnAccept.setText("Proof Of Attendence")
            }else if(btnAccept.text == "Proof Of Attendence"){
                val intent = Intent(this@TaskDetailsActivity, ProofOfAttendence::class.java)
                intent.putExtra("taskid", taskId)
                startActivity(intent)
            }else if(btnAccept.text == "Add Instruction Steps"){
            val intent = Intent(this@TaskDetailsActivity, CheckListActivity::class.java)
            intent.putExtra("taskid", taskId)
            startActivity(intent)
        }else if(btnAccept.text == "Add Pictures"){
                val intent = Intent(this@TaskDetailsActivity, AddAttachmentActivity::class.java)
                intent.putExtra("taskid", taskId)
                startActivity(intent)
            }*/
        })

        currentStatus.setOnClickListener(View.OnClickListener { view ->

        })
    }
}