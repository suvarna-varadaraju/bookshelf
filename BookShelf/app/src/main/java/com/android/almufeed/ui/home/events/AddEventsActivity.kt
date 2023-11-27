package com.android.almufeed.ui.home.events

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import com.android.almufeed.R
import com.android.almufeed.business.domain.state.DataState
import com.android.almufeed.business.domain.utils.exhaustive
import com.android.almufeed.databinding.ActivityAddEventsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddEventsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddEventsBinding
    private val addEventsViewModel: AddEventsViewModel by viewModels()
    private lateinit var pd : Dialog
    var imageType = arrayOf("Select an Event", "Under DLP", "Material Collected and On site"," Customer request to reschedule","Inspection Completed",
    "In progress","Out of scope","Call back requested","Quotation pending","No access")
    private var selectedImageType : Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEventsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pd = Dialog(this, android.R.style.Theme_Black)
        val view: View = LayoutInflater.from(this).inflate(R.layout.remove_border, null)
        pd.requestWindowFeature(Window.FEATURE_NO_TITLE)
        pd.getWindow()!!.setBackgroundDrawableResource(R.color.transparent)
        pd.setContentView(view)
        pd.show()

        addEventsViewModel.requestForEvent()
        subscribeObservers()
       /* binding.spinnerType.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                selectedImageType = position - 1
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }*/
    }

    private fun subscribeObservers() {
        addEventsViewModel.myEventDataSTate.observe(this@AddEventsActivity) { dataState ->
            when (dataState) {
                is DataState.Error -> {
                    Toast.makeText(this@AddEventsActivity,"Something went wrong", Toast.LENGTH_SHORT).show()
                }
                is DataState.Loading -> {

                }
                is DataState.Success -> {
                    Log.e("AR_MYBUSS::", "UI Details: ${dataState.data}")
                    pd.dismiss()
                    val adapter = ArrayAdapter(this, R.layout.simple_spinner_item,imageType)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.spinnerType.adapter = adapter
                }
            }.exhaustive
        }
    }
}