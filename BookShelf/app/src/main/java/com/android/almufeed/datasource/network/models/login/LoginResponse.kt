package com.android.almufeed.datasource.network.models.login

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @SerializedName("ResourceID")
    @Expose
    val ResourceID: String,
    @SerializedName("description")
    @Expose
    val description: String,
    @SerializedName("descipline")
    @Expose
    val descipline: String,
    @SerializedName("Success")
    @Expose
    val Success: Boolean,
)