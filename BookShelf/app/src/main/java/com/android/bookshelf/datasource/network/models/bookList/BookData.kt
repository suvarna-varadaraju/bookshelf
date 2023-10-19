package com.android.bookshelf.datasource.network.models.bookList

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BookData(
    @SerializedName("kind")
    @Expose
    val kind: String,

    @SerializedName("id")
    @Expose
    val id: String,

    @SerializedName("etag")
    @Expose
    val etag: String,

    @SerializedName("selfLink")
    @Expose
    val selfLink: String,

    @SerializedName("volumeInfo")
    @Expose
    val volumeInfo: VolumeInfo,

    @SerializedName("saleInfo")
    @Expose
    val saleInfo: SaleInfo,

    @SerializedName("accessInfo")
    @Expose
    val accessInfo: AccessInfo,

    @SerializedName("searchInfo")
    @Expose
    val searchInfo: SearchInfo,
)

data class VolumeInfo(

    @SerializedName("title")
    @Expose
    val title: String,

    @SerializedName("subtitle")
    @Expose
    val subtitle: String,

    @SerializedName("authors")
    @Expose
    val authors: List<String>,

    @SerializedName("publisher")
    @Expose
    val publisher: String?,

    @SerializedName("publishedDate")
    @Expose
    val publishedDate: String?,

    @SerializedName("description")
    @Expose
    val description: String?,

    @SerializedName("industryIdentifiers")
    @Expose
    val industryIdentifiers: List<IndustryIdentifiers>,

    @SerializedName("readingModes")
    @Expose
    val readingModes: ReadingModes,

    @SerializedName("pageCount")
    @Expose
    val pageCount: Int,

    @SerializedName("printType")
    @Expose
    val printType: String,

    @SerializedName("categories")
    @Expose
    val categories: List<String>?,

    @SerializedName("averageRating")
    @Expose
    val averageRating: Int,

    @SerializedName("ratingsCount")
    @Expose
    val ratingsCount: Int,

    @SerializedName("maturityRating")
    @Expose
    val maturityRating: String,

    @SerializedName("allowAnonLogging")
    @Expose
    val allowAnonLogging: Boolean,

    @SerializedName("contentVersion")
    @Expose
    val contentVersion: String,

    @SerializedName("panelizationSummary")
    @Expose
    val panelizationSummary: PanelizationSummary,

    @SerializedName("imageLinks")
    @Expose
    val imageLinks: ImageLinks?,

    @SerializedName("language")
    @Expose
    val language: String,

    @SerializedName("previewLink")
    @Expose
    val previewLink: String?,

    @SerializedName("infoLink")
    @Expose
    val infoLink: String?,

    @SerializedName("canonicalVolumeLink")
    @Expose
    val canonicalVolumeLink: String?,
)
data class SaleInfo(

    @SerializedName("country")
    @Expose
    val country: String,

    @SerializedName("saleability")
    @Expose
    val saleability: String,

    @SerializedName("isEbook")
    @Expose
    val isEbook: Boolean
)
data class AccessInfo(

    @SerializedName("country")
    @Expose
    val country: String,

    @SerializedName("viewability")
    @Expose
    val viewability: String,

    @SerializedName("embeddable")
    @Expose
    val embeddable: Boolean,

    @SerializedName("publicDomain")
    @Expose
    val publicDomain: Boolean,

    @SerializedName("textToSpeechPermission")
    @Expose
    val textToSpeechPermission: String,

    @SerializedName("epub")
    @Expose
    val epub: Epub,

    @SerializedName("pdf")
    @Expose
    val pdf: Pdf,

    @SerializedName("webReaderLink")
    @Expose
    val webReaderLink: String,

    @SerializedName("accessViewStatus")
    @Expose
    val accessViewStatus: String,

    @SerializedName("quoteSharingAllowed")
    @Expose
    val quoteSharingAllowed: String,
)
data class SearchInfo(
    @SerializedName("textSnippet")
    @Expose
    val textSnippet: String,
)
data class Epub(
    @SerializedName("isAvailable")
    @Expose
    val isAvailable: Boolean,
)
data class Pdf(
    @SerializedName("isAvailable")
    @Expose
    val isAvailable: Boolean,

    @SerializedName("acsTokenLink")
    @Expose
    val acsTokenLink: String,
)

data class IndustryIdentifiers(
    @SerializedName("type")
    @Expose
    val type: String,

    @SerializedName("identifier")
    @Expose
    val identifier: String,
)

data class ReadingModes(
    @SerializedName("image")
    @Expose
    val image: Boolean,

    @SerializedName("text")
    @Expose
    val text: Boolean,
)

data class PanelizationSummary(
    @SerializedName("containsEpubBubbles")
    @Expose
    val containsEpubBubbles: Boolean,

    @SerializedName("containsImageBubbles")
    @Expose
    val containsImageBubbles: Boolean,
)

data class ImageLinks(
    @SerializedName("smallThumbnail")
    @Expose
    val smallThumbnail: String?,

    @SerializedName("thumbnail")
    @Expose
    val thumbnail: String?
)