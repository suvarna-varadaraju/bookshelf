package com.android.almufeed.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.android.almufeed.R
import com.android.almufeed.databinding.ActivityRiskAssessmentBinding
import com.android.almufeed.databinding.ActivityTaskBinding

class RiskAssessment : AppCompatActivity() {
    private lateinit var binding: ActivityRiskAssessmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRiskAssessmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var taskId = intent.getStringExtra("taskid").toString()
        binding.toolbar.aboutus.setText("Task : " + taskId)
        binding.toolbar.incToolbarImage.visibility = View.VISIBLE

        binding.toolbar.incToolbarImage.setOnClickListener (View.OnClickListener { view ->
            this@RiskAssessment.onBackPressedDispatcher.onBackPressed()
        })

        binding.btnSafe.setOnClickListener (View.OnClickListener { view ->
            val intent = Intent(this@RiskAssessment, TaskDetailsActivity::class.java)
            intent.putExtra("status", "Risk Assessment Completed")
            startActivity(intent)
        })
    }
}