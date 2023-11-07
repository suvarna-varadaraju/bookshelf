package com.android.almufeed.ui.launchpad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.almufeed.databinding.ActivityDashboardBinding
import com.android.almufeed.ui.home.DocumentActivity
import com.android.almufeed.ui.home.PDFVIew
import com.android.almufeed.ui.home.TaskActivity

class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {

            txtDoc.setOnClickListener {
                Intent(this@DashboardActivity, DocumentActivity::class.java).apply {
                    startActivity(this)
                }
            }

            txtActivity.setOnClickListener {
                Intent(this@DashboardActivity, TaskActivity::class.java).apply {
                    startActivity(this)
                }
            }
        }
    }
}