package com.android.bookshelf.datasource.network.mappers

import com.android.bookshelf.business.domain.model.bookModel.BookInfo
import com.android.bookshelf.business.domain.model.bookModel.ImageLinksData
import com.android.bookshelf.business.domain.model.bookModel.VolumeInfoData
import com.android.bookshelf.business.domain.utils.EntityMapper
import com.android.bookshelf.datasource.network.models.bookList.BookData
import com.android.bookshelf.datasource.network.models.bookList.ImageLinks
import com.android.bookshelf.datasource.network.models.bookList.VolumeInfo
import javax.inject.Inject

class BookListMapper
@Inject constructor(): EntityMapper<BookData, BookInfo> {
    override fun mapFromEntity(entity: BookData): BookInfo {
        return BookInfo(
            kind = entity.kind,
            id = entity.id,
            etag = entity.etag,
            selfLink = entity.selfLink,
            volumeInfo = volumeInfo(entity.volumeInfo)
        )
    }

    private fun volumeInfo(volume: VolumeInfo): VolumeInfoData {
        return VolumeInfoData(
            title = volume.title,
            authors = volume.authors,
            publisher = volume.publisher,
            description = volume.description,
            publishedDate = volume.publishedDate,
            pageCount = volume.pageCount,
            printType = volume.printType,
            categories = volume.categories,
            averageRating = volume.averageRating,
            imageLinks = imageLink(volume.imageLinks),
            previewLink = volume.previewLink,
            infoLink = volume.infoLink,
            canonicalVolumeLink = volume.canonicalVolumeLink
        )
    }

    private fun imageLink(image: ImageLinks?): ImageLinksData? {
        return ImageLinksData(
            smallThumbnail = image?.smallThumbnail,
            thumbnail = image?.thumbnail
        )
    }

    override fun mapToEntity(domainModel: BookInfo): BookData {
        TODO("Not yet implemented")
    }

    fun mapFromEntityList(entities: List<BookData>): List<BookInfo> {
        return entities.map { mapFromEntity(it) }
    }
}