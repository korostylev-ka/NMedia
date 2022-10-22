package ru.netology.nmedia.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.dto.Post


class PostRepositoryFileImpl(private val context: Context): PostRepository {
    private var nextId = 1L
    private var posts = listOf(
        Post(
            id = nextId++,
            "Нетология. Университет интернет-профессий будущего",
            "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            "21 мая в 18:36",
            999,
            999,
            500_000,
            false
        ),
        Post(
            id = nextId++,
            "Нетология2. Университет интернет-профессий будущего",
            "Hello, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            "21 мая в 18:36",
            999,
            999_999,
            1_500_000,
            false
        ),
        Post(
            id = nextId++,
            "Нетология3. Университет интернет-профессий будущего",
            "Hi, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            "21 мая в 18:36",
            999,
            999_999,
            1_500_000,
            false,
            video = "https://www.youtube.com/watch?v=WhWc3b3KhnY"
        )
    )

    private val gson = Gson()
    private val filename = "post.json"
    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type

    private val data = MutableLiveData(posts)

    init {
        val file = context.filesDir.resolve(filename)
        if (file.exists()) {
            context.openFileInput(filename).bufferedReader().use{
                posts = gson.fromJson(it, type)
                data.value = posts
                sync()
            }
        } else {
            sync()
        }
    }
    private fun sync() {
        context.openFileOutput(filename, Context.MODE_PRIVATE).bufferedWriter().use {
            it.write(gson.toJson(posts))
        }

    }

    override fun getAll(): LiveData<List<Post>> = data
    override fun removeById(postId: Long) {
        posts = posts.filter { it.id != postId }
        data.value = posts
        sync()
    }
    override fun likeById(id: Long) {
        //проходим по коллекции, проверяем есть ли пост с данным id
        posts = posts.map {
            if (it.id != id) it else {
                it.copy(likedByMe = !it.likedByMe, likes = (if (!it.likedByMe) ++it.likes else --it.likes))
            }
        }
        //обновляем livedata(получаем новые данные где подписались)
        data.value = posts
        sync()
    }

    override fun shareById(id: Long) {
        posts = posts.map{
            if (it.id != id) it else {
                it.copy(shares = it.shares + 1)
            }
        }
        data.value = posts
        sync()

    }
    override fun save(post: Post) {
        if (post.id == 0L) {
            posts = listOf(
                post.copy(
                    //если первого элемента нет, то id 1
                    //id = posts.firstOrNull()?.id ?: 1L
                    id = nextId++
                )
            ) + posts
            data.value = posts
            sync()
            return
        }
        posts = posts.map {
            if (it.id == post.id) {
                it.copy(content = post.content)
            } else {
                it
            }
        }
        data.value = posts
        sync()
    }
}