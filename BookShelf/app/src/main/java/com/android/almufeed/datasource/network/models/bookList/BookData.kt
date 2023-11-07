package com.android.almufeed.datasource.network.models.bookList

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BookData(
    @SerializedName("id")
    @Expose
    val id: String,

    @SerializedName("scheduledDate")
    @Expose
    val scheduledDate: String,

    @SerializedName("attendDate")
    @Expose
    val attendDate: String,

    @SerializedName("building")
    @Expose
    val building: String,

    @SerializedName("location")
    @Expose
    val location: String,

    @SerializedName("type")
    @Expose
    val type: String,

    @SerializedName("priority")
    @Expose
    val priority: String,

    @SerializedName("problem")
    @Expose
    val problem: String,

    @SerializedName("category")
    @Expose
    val category: String,
)