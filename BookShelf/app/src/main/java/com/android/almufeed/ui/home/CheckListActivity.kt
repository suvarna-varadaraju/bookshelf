package com.android.almufeed.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.android.almufeed.R
import com.android.almufeed.databinding.ActivityCheckListBinding
import com.android.almufeed.databinding.ActivityTaskBinding

class CheckListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCheckListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = getIntent()
        var taskId = intent.getStringExtra("taskid").toString()
        binding.toolbar.aboutus.setText("Task : " + taskId)
        binding.toolbar.incToolbarImage.visibility = View.VISIBLE

        binding.toolbar.incToolbarImage.setOnClickListener (View.OnClickListener { view ->
            this@CheckListActivity.onBackPressedDispatcher.onBackPressed()
        })

        binding.checklist.btnYes.setOnClickListener(View.OnClickListener { view ->
            binding.checklist.btnYes.setTextColor(resources.getColor(R.color.white))
            binding.checklist.btnYes.setBackgroundColor(resources.getColor(R.color.primary))
            binding.checklist.btnNo.setTextColor(resources.getColor(R.color.black))
            binding.checklist.btnNo.setBackgroundColor(resources.getColor(R.color.white))
        })

        binding.checklist.btnNo.setOnClickListener(View.OnClickListener { view ->
            binding.checklist.btnNo.setTextColor(resources.getColor(R.color.white))
            binding.checklist.btnNo.setBackgroundColor(resources.getColor(R.color.primary))
            binding.checklist.btnYes.setTextColor(resources.getColor(R.color.black))
            binding.checklist.btnYes.setBackgroundColor(resources.getColor(R.color.white))
        })

        binding.checklist1.btnYes.setOnClickListener(View.OnClickListener { view ->
            binding.checklist1.btnYes.setTextColor(resources.getColor(R.color.white))
            binding.checklist1.btnYes.setBackgroundColor(resources.getColor(R.color.primary))
            binding.checklist1.btnNo.setTextColor(resources.getColor(R.color.black))
            binding.checklist1.btnNo.setBackgroundColor(resources.getColor(R.color.white))
        })

        binding.checklist1.btnNo.setOnClickListener(View.OnClickListener { view ->
            binding.checklist1.btnNo.setTextColor(resources.getColor(R.color.white))
            binding.checklist1.btnNo.setBackgroundColor(resources.getColor(R.color.primary))
            binding.checklist1.btnYes.setTextColor(resources.getColor(R.color.black))
            binding.checklist1.btnYes.setBackgroundColor(resources.getColor(R.color.white))
        })

        binding.btnAccept.setOnClickListener(View.OnClickListener { view ->
            val intent = Intent(this@CheckListActivity, TaskDetailsActivity::class.java)
            intent.putExtra("taskid", taskId)
            intent.putExtra("status", "Add Pictures")
            startActivity(intent)
        })
    }
}