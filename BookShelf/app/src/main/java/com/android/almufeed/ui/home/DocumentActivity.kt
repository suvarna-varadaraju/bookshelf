package com.android.almufeed.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.android.almufeed.R
import com.android.almufeed.databinding.ActivityDocumentBinding

class DocumentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDocumentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDocumentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            toolbar.aboutus.setText("Documents")
            binding.toolbar.incToolbarImage.visibility = View.VISIBLE

            binding.toolbar.incToolbarImage.setOnClickListener (View.OnClickListener { view ->
                this@DocumentActivity.onBackPressedDispatcher.onBackPressed()
            })

            txtDoc1.setOnClickListener {
                val intent = Intent(this@DocumentActivity, PDFVIew::class.java)
                intent.putExtra("url","workplace.pdf")
                startActivity(intent)
            }

            txtDoc2.setOnClickListener {
                val intent = Intent(this@DocumentActivity, PDFVIew::class.java)
                intent.putExtra("url","customerServicePPM.pdf")
                startActivity(intent)
            }

            txtDoc3.setOnClickListener {
                val intent = Intent(this@DocumentActivity, PDFVIew::class.java)
                intent.putExtra("url","training_PPM.pdf")
                startActivity(intent)
            }
        }
    }
}