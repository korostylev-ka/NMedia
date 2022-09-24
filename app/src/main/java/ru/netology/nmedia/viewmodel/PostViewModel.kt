package ru.netology.nmedia.viewmodel

import androidx.constraintlayout.widget.Group
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.R
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryInMemoryImpl

private val empty = Post(
    0L,
    "",
    "",
    "",
    0,
    0,
    0,
    false

)


class PostViewModel: ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data = repository.getAll()
    val edited = MutableLiveData(empty)
    //"заглушка" с пустым постом

    fun likeById(id: Long) = repository.likeById(id)
    fun shareById(id: Long) = repository.shareById(id)
    fun removeById(postId: Long) = repository.removeById(postId)

    //функция сохранения контента(после изменения данных мы отправим в репозиторий измененный пост и сбросим в дефолтное значение
    fun save() {
        edited.value?.let {
            repository.save(it)
        }
        edited.value = empty
    }

    //функция изменения контента
    fun changeContent(content: String) {
        //если контент совпал, выходим
        if (content == edited.value?.content) {
            return
        }
        edited.value = edited.value?.copy(content = content)
    }

    //функция редактирования
    fun edit(post: Post){
        edited.value = post


    }



}


