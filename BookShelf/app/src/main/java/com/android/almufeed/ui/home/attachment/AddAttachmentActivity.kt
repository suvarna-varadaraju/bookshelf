package com.android.almufeed.ui.home.attachment

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.android.almufeed.R
import com.android.almufeed.business.domain.state.DataState
import com.android.almufeed.business.domain.utils.exhaustive
import com.android.almufeed.databinding.ActivityAddAttachmentBinding
import com.android.almufeed.ui.home.events.AddEventsActivity
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.Date

@AndroidEntryPoint
class AddAttachmentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddAttachmentBinding
    private val addAttachmentViewModel: AddAttachmentViewModel by viewModels()
    var imageType = arrayOf("Select an image type", "Before", "After","Material Picture","Inspection")
    private val REQUEST_PERMISSION = 100
    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_PICK_IMAGE = 2
    private lateinit var pd : Dialog
    private var convertedImage1 : String = ""
    private var convertedImage2 : String = ""
    private var convertedImage3 : String = ""
    private var selectedImageType : Int = -1
    private lateinit var taskId : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddAttachmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = getIntent()
        taskId = intent.getStringExtra("taskid").toString()
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
               /* type 0 = before
                type 1 = after
                type 2 = material
                type 3 = inspection*/
                selectedImageType = position - 1
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

            if(selectedImageType < 0){
                Toast.makeText(this@AddAttachmentActivity,"Please select image type", Toast.LENGTH_SHORT).show()
            }else{
                pd = Dialog(this, android.R.style.Theme_Black)
                val view: View = LayoutInflater.from(this).inflate(com.android.almufeed.R.layout.remove_border, null)
                pd.requestWindowFeature(Window.FEATURE_NO_TITLE)
                pd.getWindow()!!.setBackgroundDrawableResource(com.android.almufeed.R.color.transparent)
                pd.setContentView(view)
                pd.show()
                addAttachmentViewModel.requestForImage(convertedImage1,convertedImage2,convertedImage3,selectedImageType,binding.etDescription.text.toString(),taskId,"tab1")
            }

            /*val intent = Intent(this@AddAttachmentActivity, RatingActivity::class.java)
            intent.putExtra("taskid", taskId)
            startActivity(intent)*/
        }

        subscribeObservers()
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
                val iv = ImageView(this)
                val param: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
                )

                param.setMargins(0, 0, 10, 0)
                iv.setLayoutParams(param)
                iv.setImageBitmap(bitmap)
                iv.setAdjustViewBounds(true)
                binding.linImage?.addView(iv)
                //binding.captureImage?.setImageBitmap(bitmap)

                val baos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos) // bm is the bitmap object
                val byteArrayImage: ByteArray = baos.toByteArray()
                convertedImage1 = Base64.encodeToString(byteArrayImage, Base64.DEFAULT)
            }
            else if (requestCode == REQUEST_PICK_IMAGE) {
                val uri = data?.getData()
                val iv = ImageView(this)
                val param: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
                )

                param.setMargins(0, 0, 10, 0)
                iv.setLayoutParams(param)
                iv.setImageURI(uri)
                iv.setAdjustViewBounds(true)
                binding.linImage?.addView(iv)
                //binding.captureImage?.setImageURI(uri)

                val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
                val baos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos) // bm is the bitmap object
                val byteArrayImage: ByteArray = baos.toByteArray()
                convertedImage1 = Base64.encodeToString(byteArrayImage, Base64.DEFAULT)
            }
        }
    }

    private fun subscribeObservers() {
        addAttachmentViewModel.myImageDataSTate.observe(this@AddAttachmentActivity) { dataState ->
            when (dataState) {
                is DataState.Error -> {
                    Toast.makeText(this@AddAttachmentActivity,"Something went wrong", Toast.LENGTH_SHORT).show()
                }
                is DataState.Loading -> {

                }
                is DataState.Success -> {
                    Log.e("AR_MYBUSS::", "UI Details: ${dataState.data}")
                    pd.dismiss()
                    val intent = Intent(this@AddAttachmentActivity, AddEventsActivity::class.java)
                    intent.putExtra("taskid", taskId)
                    startActivity(intent)
                }
            }.exhaustive
        }
    }
}