package ru.netology.nmedia.dto

import android.os.Parcel
import android.os.Parcelable

data class Post(
    val id: Long,
    val author: String?,
    val content: String?,
    val published: String?,
    var likes: Long,
    var shares: Long,
    var views: Long,
    var likedByMe: Boolean
): Parcelable {
    var video: String = ""

    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readLong(),
        parcel.readLong(),
        parcel.readLong(),
        parcel.readByte() != 0.toByte()
    ) {
        video = parcel.readString().toString()
    }

    //констуктор при наличии ссылки на видео
    constructor(
        id: Long,
        author: String,
        content: String,
        published: String,
        likes: Long,
        shares: Long,
        views: Long,
        likedByMe: Boolean,
        video: String): this(id, author, content, published, likes, shares, views, likedByMe) {
            this.video = video
        }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<Post> {
        override fun createFromParcel(parcel: Parcel): Post {
            return Post(parcel)
        }

        override fun newArray(size: Int): Array<Post?> {
            return arrayOfNulls(size)
        }
    }
}