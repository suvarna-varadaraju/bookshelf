package com.android.almufeed.datasource.network.models.events

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GetEventListResponseModel (
    @SerializedName("Success")
    @Expose
    val Success: Boolean
    )
