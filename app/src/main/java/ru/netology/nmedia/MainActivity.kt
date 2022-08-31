package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.netology.nmedia.databinding.ActivityMainBinding

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    var likes: Long = 999,
    var shares: Long = 999_998,
    var views: Long = 0,
    var likedByMe: Boolean = false
)

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

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val post = Post(
            1,
            "Нетология. Университет интернет-профессий будущего",
            "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            "21 мая в 18:36"
        )
        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            likes.text = PostService.showValues(post.likes)
            shares.text = PostService.showValues(post.shares)
            views.text = PostService.showValues(post.views)
            if (post.likedByMe) {
                like?.setImageResource(R.drawable.ic_baseline_favorite_24)
            }
            //обработка нажатия на кнопку Нравится
            like?.setOnClickListener {
                post.likedByMe = !post.likedByMe
                like.setImageResource(
                    if (post.likedByMe) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_baseline_favorite_border_24
                )
                likes.setText(
                    if (post.likedByMe) PostService.showValues(++post.likes) else PostService.showValues(--post.likes)
                )
            }

            //обработка нажатия на кнопку Поделиться
            share?.setOnClickListener {
                shares.setText(
                    //(++post.shares).toString()
                PostService.showValues(++post.shares)
                )
            }
        }
    }
}