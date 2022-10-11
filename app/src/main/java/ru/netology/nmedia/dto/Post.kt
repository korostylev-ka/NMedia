package ru.netology.nmedia.dto

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    var likes: Long,
    var shares: Long,
    var views: Long,
    var likedByMe: Boolean
) {
    var video: String = ""
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
}