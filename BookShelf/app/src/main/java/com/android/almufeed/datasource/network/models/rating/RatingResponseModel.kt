package com.android.almufeed.datasource.network.models.rating

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RatingResponseModel (
    @SerializedName("Success")
    @Expose
    val Success: Boolean
        )