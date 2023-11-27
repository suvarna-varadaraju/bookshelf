package com.android.almufeed.datasource.network.models.attachment

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AttachmentResponseModel (
    @SerializedName("Success")
    @Expose
    val Success: Boolean
)