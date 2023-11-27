package com.android.almufeed.datasource.network.models.updateInstruction

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UpdateInstructionSetResponseModel (
    @SerializedName("Success")
    @Expose
    val Success: Boolean
    )