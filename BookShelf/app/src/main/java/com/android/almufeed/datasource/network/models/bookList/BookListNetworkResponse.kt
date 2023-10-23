package com.android.almufeed.datasource.network.models.bookList

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BookListNetworkResponse(

    @SerializedName("items")
    @Expose
    val `items`: List<BookData>,
    @SerializedName("kind")
    @Expose
    val kind: String,
    @SerializedName("totalItems")
    @Expose
    val totalItems: Int

)
