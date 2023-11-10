package com.android.almufeed.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.android.almufeed.R
import com.android.almufeed.databinding.ActivityAddAttachmentBinding
import java.io.FileDescriptor
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date

class AddAttachmentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddAttachmentBinding
    var imageType = arrayOf("Select an image type", "After", "Before","Inspection","Material Picture")
    private val REQUEST_PERMISSION = 100
    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_PICK_IMAGE = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddAttachmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = getIntent()
        var taskId = intent.getStringExtra("taskid").toString()
        binding.toolbar.aboutus.setText("Task : " + taskId)
        binding.toolbar.incToolbarImage.visibility = View.VISIBLE
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm")
        val currentDate = sdf.format(Date())
        binding.txtCurrentDateTime.setText(currentDate)

        binding.toolbar.incToolbarImage.setOnClickListener (View.OnClickListener { view ->
            this@AddAttachmentActivity.onBackPressedDispatcher.onBackPressed()
        })

        val adapter = ArrayAdapter(this, R.layout.simple_spinner_item, imageType)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerType.adapter = adapter

        binding.spinnerType.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    //Toast.makeText(this@AddAttachmentActivity, getString(R.string.selected_item) + " " + "" + languages[position], Toast.LENGTH_SHORT).show()
                 }

            override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
             }
            }

        binding.btnImage.setOnClickListener {
            openCamera()
        }
        binding.btnGallery.setOnClickListener {
            openGallery()
        }

        binding.btnSave.setOnClickListener {
            val intent = Intent(this@AddAttachmentActivity, RatingActivity::class.java)
            intent.putExtra("taskid", taskId)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        checkCameraPermission()
    }

    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.CAMERA),
                REQUEST_PERMISSION)
        }
    }
    private fun openCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intent ->
            intent.resolveActivity(packageManager)?.also {
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }
    private fun openGallery() {
        Intent(Intent.ACTION_GET_CONTENT).also { intent ->
            intent.type = "image/*"
            intent.resolveActivity(packageManager)?.also {
                startActivityForResult(intent, REQUEST_PICK_IMAGE)
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                val bitmap = data?.extras?.get("data") as Bitmap
                binding.captureImage.setImageBitmap(bitmap)
            }
            else if (requestCode == REQUEST_PICK_IMAGE) {
                val uri = data?.getData()
                binding.captureImage.setImageURI(uri)
            }
        }
    }
}