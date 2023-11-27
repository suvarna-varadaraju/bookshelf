package com.android.almufeed.ui.home.instructionSet

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.almufeed.R
import com.android.almufeed.databinding.RecyclerInstructionadapterBinding
import com.android.almufeed.datasource.network.models.instructionSet.InstructionData
import com.android.almufeed.datasource.network.models.instructionSet.InstructionSetResponseModel

class InstructionRecyclerAdapter (val instructionList: InstructionSetResponseModel,
                                  val context: Context,
                                  private val listener: OnItemClickListener
) : RecyclerView.Adapter<InstructionRecyclerAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = RecyclerInstructionadapterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = instructionList.problem.get(position)
        if (currentItem != null) {
            holder.bind(currentItem,position)
        }
    }

    inner class ItemViewHolder(private val binding: RecyclerInstructionadapterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(currentItem: InstructionData, position: Int) {
            binding.apply {

                /*feedbacktype = 0 = nothing
                feedbacktype = 1 = yesno
                feedbacktype = 2 = heading
                feedbacktype = 3 = freetext*/

                txtProblem.text = currentItem.LineNumber.toString() + " . " + currentItem.Steps
                when(currentItem.FeedbackType) {
                    0 -> {
                        binding.checklist.linYesno.visibility = View.GONE
                        binding.etMessage.visibility = View.GONE
                    }

                    1 -> {
                        binding.checklist.linYesno.visibility = View.VISIBLE
                        binding.etMessage.visibility = View.GONE
                    }

                    2 -> {
                        binding.checklist.linYesno.visibility = View.VISIBLE
                        binding.etMessage.visibility = View.GONE
                    }

                    3 -> {
                        binding.checklist.linYesno.visibility = View.GONE
                        binding.etMessage.visibility = View.VISIBLE
                    }
                    else -> print("I don't know anything about it")
                }

               binding.checklist.btnYes.setOnClickListener(View.OnClickListener { view ->
                    binding.checklist.btnYes.setTextColor(context.getColor(R.color.white))
                    binding.checklist.btnYes.setBackgroundColor(context.getColor(R.color.primary))
                    binding.checklist.btnNo.setTextColor(context.getColor(R.color.black))
                    binding.checklist.btnNo.setBackgroundColor(context.getColor(R.color.white))
                   listener.onItemClick(currentItem.Refrecid, currentItem.FeedbackType,"Yes")
                })

                binding.checklist.btnNo.setOnClickListener(View.OnClickListener { view ->
                    binding.checklist.btnNo.setTextColor(context.getColor(R.color.white))
                    binding.checklist.btnNo.setBackgroundColor(context.getColor(R.color.primary))
                    binding.checklist.btnYes.setTextColor(context.getColor(R.color.black))
                    binding.checklist.btnYes.setBackgroundColor(context.getColor(R.color.white))
                    listener.onItemClick(currentItem.Refrecid, currentItem.FeedbackType,"No")
                })

               /* itemView.rootView.setOnClickListener {
                    val yesColor = (binding.checklist.btnYes.background as ColorDrawable).color
                    val noColor = (binding.checklist.btnNo.background as ColorDrawable).color
                    when(yesColor) {
                        R.color.primary -> {
                            listener.onItemClick(currentItem.Refrecid, currentItem.FeedbackType,"Yes")
                        }
                    }

                    when(noColor) {
                        R.color.primary -> {
                            listener.onItemClick(currentItem.Refrecid, currentItem.FeedbackType,"No")
                        }
                    }
                }*/
            }
        }
    }

    override fun getItemCount(): Int {
        return instructionList.problem.size
    }

    interface OnItemClickListener {
        fun onItemClick(refId: Long, feedBackType: Int, answer: String)
    }
}