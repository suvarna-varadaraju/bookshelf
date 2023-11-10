package com.android.almufeed.ui.home

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import com.android.almufeed.R
import com.android.almufeed.databinding.ActivityRatingBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.kyanogen.signatureview.SignatureView

class RatingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRatingBinding

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
                    mDialog.dismiss()
                }else if(tag == "technician"){
                    binding.imgSignatureTech!!.visibility = View.VISIBLE
                    binding.imgSignatureTech!!.setImageBitmap(bitmap)
                    mDialog.dismiss()
                }
            }
        }
    }

    private fun showMessageDialog() {
        val builder = AlertDialog.Builder(this)
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
    }
}