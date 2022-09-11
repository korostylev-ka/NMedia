package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post

class PostRepositoryInMemoryImpl: PostRepository {
    private var post = Post(
        1,
        "Нетология. Университет интернет-профессий будущего",
        "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
        "21 мая в 18:36",
        999,
        999_999,
        1_500_000,
        false
    )

    private val data = MutableLiveData(post)
    override fun get(): LiveData<Post> = data
    //функция выставления/снятия лайка
    override fun like() {
        if (!post.likedByMe) PostService.showValues(++post.likes) else PostService.showValues(--post.likes)
        post = post.copy(likedByMe =  !post.likedByMe)
        data.value = post
    }
    //функция поделиться
    override fun share() {
        PostService.showValues(++post.shares)
        post = post.copy(shares = ++post.shares)
        data.value = post
    }

}

object PostService{
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
                displayValue = valueToString[0].toString() + valueToString[1].toString() + valueToString[2].toString() + "К"
            }
            in 1_000_000..Long.MAX_VALUE -> {
                displayValue = valueToString[0].toString() + "." + valueToString[1].toString() + "М"
            }
        }
        return displayValue
    }

}