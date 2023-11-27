package com.android.almufeed.datasource.network.models.attachment

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AttachmentData (
    @SerializedName("Image1")
    @Expose
    val Image1: String,

    @SerializedName("Image2")
    @Expose
    val Image2: String,

    @SerializedName("Image3")
    @Expose
    val Image3: String,

    @SerializedName("type")
    @Expose
    val type: Int,

    @SerializedName("description")
    @Expose
    val description: String,

    @SerializedName("taskId")
    @Expose
    val taskId: String,

    @SerializedName("resource")
    @Expose
    val resource: String
    )