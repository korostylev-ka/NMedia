package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post

class PostRepositoryInMemoryImpl: PostRepository {
    //уникальный id поста
    private var nextId = 1L
    //список постов
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

    private val data = MutableLiveData(posts)
    override fun getAll(): LiveData<List<Post>> = data
    //функция выставления/снятия лайка
    override fun likeById(id: Long) {
        //проходим по коллекции, проверяем есть ли пост с данным id
        posts = posts.map {
            if (it.id != id) it else {
                it.copy(likedByMe = !it.likedByMe, likes = (if (!it.likedByMe) ++it.likes else --it.likes))
            }
        }
        //обновляем livedata(получаем новые данные где подписались)
        data.value = posts
    }

    //функция поделиться
    override fun shareById(id: Long) {
        posts = posts.map{
            if (it.id != id) it else {
                it.copy(shares = it.shares + 1)
             }
        }
        data.value = posts

    }
    //функция удалить
    override fun removeById(postId: Long) {
        posts = posts.filter { it.id != postId }
        data.value = posts
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
    }

object PostService {
    //функция вывода просмотров/лайков и т.д в виде 1.1К/1.3М и т.д.
    fun showValues(value: Long): String {
        var valueToString = value.toString()
        var displayValue = ""
        when (value) {
            in 0..999 -> displayValue = value.toString()
            in 1_000..9_999 -> {
                displayValue = valueToString[0].toString() + "." + valueToString[1].toString() + "К"
            }
            in 10_000..99_999 -> {
                displayValue = valueToString[0].toString() + valueToString[1].toString() + "К"
            }
            in 100_000..999_999 -> {
                displayValue =
                    valueToString[0].toString() + valueToString[1].toString() + valueToString[2].toString() + "К"
            }
            in 1_000_000..Long.MAX_VALUE -> {
                displayValue = valueToString[0].toString() + "." + valueToString[1].toString() + "М"
            }
        }
        return displayValue
    }
}

}