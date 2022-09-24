package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import ru.netology.nmedia.dto.Post

interface PostRepository {
    fun getAll(): LiveData<List<Post>>
    //поставить лайк
    fun likeById(id: Long)
    //поделиться
    fun shareById(id: Long)
    fun removeById(postId: Long)
    fun save(post: Post)
}