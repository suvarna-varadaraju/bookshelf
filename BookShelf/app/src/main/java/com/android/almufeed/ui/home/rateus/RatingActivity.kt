package com.android.almufeed.ui.home.rateus

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import com.android.almufeed.R
import com.android.almufeed.business.domain.state.DataState
import com.android.almufeed.business.domain.utils.exhaustive
import com.android.almufeed.databinding.ActivityRatingBinding
import com.android.almufeed.ui.home.events.AddEventsViewModel
import com.android.almufeed.ui.launchpad.DashboardActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.kyanogen.signatureview.SignatureView
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream

@AndroidEntryPoint
class RatingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRatingBinding
    private val ratingViewModel: RatingViewModel by viewModels()
    private lateinit var pd : Dialog
    private var customerSignature : String = ""
    private var techSignature : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRatingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var taskId = intent.getStringExtra("taskid").toString()
        binding.toolbar.aboutus.setText("Task : " + taskId)
        binding.toolbar.incToolbarImage.visibility = View.VISIBLE

        binding.toolbar.incToolbarImage.setOnClickListener (View.OnClickListener { view ->
            this@RatingActivity.onBackPressedDispatcher.onBackPressed()
        })

        binding.btnCustomer.setOnClickListener (View.OnClickListener { view ->
            showMessageDialog()
        })

        binding.btnTechnic.setOnClickListener (View.OnClickListener { view ->
            showSignatureDialog("technician")
        })

        binding.btnComplete.setOnClickListener (View.OnClickListener { view ->
            ratingViewModel.requestForRating(customerSignature,techSignature,binding.rating.rating.toDouble(),binding.emailInput.text.toString(),"",taskId,"tab1")
        })
    }

    private fun showSignatureDialog(tag:String) {
        val mBuilder = MaterialAlertDialogBuilder(this, com.google.android.material.R.style.MaterialAlertDialog_Material3)
        mBuilder.setView(layoutInflater.inflate(R.layout.dialog_signature, null))
        val mDialog = mBuilder.create()
        mDialog.setCancelable(false)
        mDialog.setCanceledOnTouchOutside(false)
        mDialog.show()

        val btnClear = mDialog.findViewById<Button>(R.id.btnClear)
        val btnSave = mDialog.findViewById<Button>(R.id.btnSave)
        val btnCancel = mDialog.findViewById<ImageView>(R.id.btnCancel)
        val signatureView = mDialog.findViewById<SignatureView>(R.id.signature_view)

        btnClear?.setOnClickListener {
            if (signatureView != null) {
                signatureView.clearCanvas()
            }
        }

        btnCancel?.setOnClickListener {
            mDialog.dismiss()
        }

        btnSave?.setOnClickListener{
            val bitmap: Bitmap = signatureView!!.getSignatureBitmap()
            if (bitmap != null) {
                if(tag == "customer"){
                    binding.imgSignatureCustomer!!.visibility = View.VISIBLE
                    binding.imgSignatureCustomer!!.setImageBitmap(bitmap)
                    val baos = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos) // bm is the bitmap object
                    val byteArrayImage: ByteArray = baos.toByteArray()
                    customerSignature = Base64.encodeToString(byteArrayImage, Base64.DEFAULT)
                    mDialog.dismiss()
                }else if(tag == "technician"){
                    binding.imgSignatureTech!!.visibility = View.VISIBLE
                    binding.imgSignatureTech!!.setImageBitmap(bitmap)
                    val baos = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos) // bm is the bitmap object
                    val byteArrayImage: ByteArray = baos.toByteArray()
                    techSignature = Base64.encodeToString(byteArrayImage, Base64.DEFAULT)
                    mDialog.dismiss()
                }
            }
        }
    }

  /*  private fun showMessageDialog() {
        val builder = AlertDialog.Builder(this@RatingActivity)
        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()

        //builder.setTitle(R.string.dialogTitle)
        //set message for alert dialog
        builder.setMessage(R.string.dialog_message)
        //builder.setIcon(android.R.drawable.ic_dialog_alert)

        //performing positive action
        builder.setPositiveButton("OK"){dialogInterface, which ->
            alertDialog.dismiss()
            showSignatureDialog("customer")
        }
    }*/

    @SuppressLint("SetTextI18n")
    private fun showMessageDialog() {
        val mBuilder = MaterialAlertDialogBuilder(this@RatingActivity, R.style.MaterialAlertDialog_Rounded)
        mBuilder.setView(layoutInflater.inflate(R.layout.alert_dialog, null))
        val mDialog = mBuilder.create()
        mDialog.setCancelable(false)
        mDialog.setCanceledOnTouchOutside(false)
        mDialog.show()

        val tvmsg = mDialog.findViewById<TextView>(R.id.tv_msg)
        val btnOk = mDialog.findViewById<Button>(R.id.btn_ok)

        btnOk?.setOnClickListener {
            mDialog.dismiss()
            showSignatureDialog("customer")
        }
    }

    private fun subscribeObservers() {
        ratingViewModel.myRateDataSTate.observe(this@RatingActivity) { dataState ->
            when (dataState) {
                is DataState.Error -> {
                    Toast.makeText(this@RatingActivity,"Something went wrong", Toast.LENGTH_SHORT).show()
                }
                is DataState.Loading -> {

                }
                is DataState.Success -> {
                    Log.e("AR_MYBUSS::", "UI Details: ${dataState.data}")
                    pd.dismiss()
                    if(dataState.data.Success){
                        Toast.makeText(this@RatingActivity,"Task Completed", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@RatingActivity, DashboardActivity::class.java)
                        intent.putExtra("taskid", taskId)
                        startActivity(intent)
                    } else {

                    }
                }
            }.exhaustive
        }
    }
}