package ru.netology.nmedia.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.nmedia.dto.Post

@Entity
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val author: String?,
    val content: String?,
    val published: String?,
    var likes: Long = 0,
    var shares: Long = 0,
    var views: Long = 0,
    val likedByMe: Boolean,

) {
    fun toDto() = Post(id, author, content, published, likes, shares, views, likedByMe)

    companion object {
        fun fromDto(dto: Post) =
            PostEntity(dto.id, dto.author, dto.content, dto.published, dto.likes, dto.shares, dto.views, dto.likedByMe)

    }
}
