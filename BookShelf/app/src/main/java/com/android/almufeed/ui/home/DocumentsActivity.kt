package com.android.almufeed.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.almufeed.R
import com.android.almufeed.databinding.ActivityDashboardBinding
import com.android.almufeed.databinding.ActivityDocumentsBinding

class DocumentsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDocumentsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDocumentsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.aboutus.setText("DOCUMENTS")


    }
}