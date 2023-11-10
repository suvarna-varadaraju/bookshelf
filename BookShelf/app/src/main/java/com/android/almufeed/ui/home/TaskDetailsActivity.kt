package com.android.almufeed.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.android.almufeed.R

class TaskDetailsActivity : AppCompatActivity(){
    lateinit var backPress: ImageView
    lateinit var aboutus: TextView
    lateinit var currentStatus: TextView
    lateinit var btnAccept: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_details)
        backPress = findViewById<ImageView>(R.id.inc_toolbar_image)
        aboutus = findViewById<TextView>(R.id.aboutus)
        btnAccept = findViewById<Button>(R.id.btn_accept)
        currentStatus = findViewById<TextView>(R.id.txt_action)
        backPress.visibility = View.VISIBLE
        val intent = getIntent()
        var taskId = intent.getStringExtra("taskid").toString()
        var currentAvailableStatus = intent.getStringExtra("status").toString()
        aboutus.setText("Task : " + taskId)
        if(currentAvailableStatus == "Risk Assessment Completed"){
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
        }
        backPress.setOnClickListener (View.OnClickListener { view ->
            this@TaskDetailsActivity.onBackPressedDispatcher.onBackPressed()
        })

        btnAccept.setOnClickListener (View.OnClickListener { view ->

            if(btnAccept.text == "Accept"){
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
            }
        })

        currentStatus.setOnClickListener(View.OnClickListener { view ->

        })
    }
}