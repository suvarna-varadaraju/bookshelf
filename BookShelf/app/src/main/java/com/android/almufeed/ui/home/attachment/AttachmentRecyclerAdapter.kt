package com.android.almufeed.ui.home.attachment

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.almufeed.databinding.RecyclerAttachmentadapterBinding
import com.android.almufeed.datasource.network.models.attachment.GetAttachmentData
import com.android.almufeed.datasource.network.models.attachment.GetAttachmentResponseModel
import com.android.almufeed.datasource.network.models.bookList.BookData
import com.android.almufeed.datasource.network.models.tasklist.TaskListResponse
import com.android.almufeed.ui.home.TaskDetailsActivity

class AttachmentRecyclerAdapter( val taskList: GetAttachmentResponseModel, val context: Context
) : RecyclerView.Adapter<AttachmentRecyclerAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = RecyclerAttachmentadapterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = taskList.fsiImage[position]
        if (currentItem != null) {
            holder.bind(currentItem,position)
        }
    }

    inner class ItemViewHolder(private val binding: RecyclerAttachmentadapterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(currentItem: GetAttachmentData, position: Int) {
            binding.apply {
                itemView.setOnClickListener {
                   /* val intent = Intent(context, TaskDetailsActivity::class.java)
                    intent.putExtra("taskid", currentItem.TaskId)
                    intent.putExtra("description", currentItem.Notes)
                    intent.putExtra("priority", currentItem.Priority)
                    intent.putExtra("scheduledate", currentItem.scheduledDate)
                    intent.putExtra("duedate", currentItem.attendDate)
                    intent.putExtra("cusname", currentItem.CustName)
                    intent.putExtra("mobile", currentItem.Phone)
                    intent.putExtra("building", currentItem.Building)
                    intent.putExtra("location", currentItem.Location)
                    intent.putExtra("status", currentItem.LOC)
                    context.startActivity(intent)*/
                }

                dateTime.text = "Date :  " + currentItem.datetime
                txtImageType.text = "Image Type :  " + currentItem.type

                /*  btnOpen.setOnClickListener {
                      val intent = Intent(context, TaskDetailsActivity::class.java)
                      intent.putExtra("taskid", currentItem.id)
                      context.startActivity(intent)
                  }

                  btnAccept.setOnClickListener {
                      val intent = Intent(context, RiskAssessment::class.java)
                      intent.putExtra("taskid", currentItem.id)
                      context.startActivity(intent)
                  }*/
            }
        }
    }

    override fun getItemCount(): Int {
        return taskList.fsiImage.size
    }
}