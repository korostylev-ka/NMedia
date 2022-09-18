//класс принимает на вход view и нужен, чтобы держать на нее ссылку, чтобы использовать для обновления элементов списка

package ru.netology.nmedia.adapter

import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepositoryInMemoryImpl

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onLikeListener: (Post) -> Unit, //слушатель
) : RecyclerView.ViewHolder(binding.root) {
    //обновляет элемент
    fun bind(post: Post) {
        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            likes.text = PostRepositoryInMemoryImpl.PostService.showValues(post.likes)
            shares.text = PostRepositoryInMemoryImpl.PostService.showValues(post.shares)
            views.text = PostRepositoryInMemoryImpl.PostService.showValues(post.views)
            like.setImageResource(
                if (post.likedByMe) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_baseline_favorite_border_24
            )
            like.setOnClickListener {
                onLikeListener(post)
            }
        }
    }

}

class PostViewHolderShare(
    private val binding: CardPostBinding,
    private val onShareListener: (Post) -> Unit, //слушатель
) : RecyclerView.ViewHolder(binding.root) {
    //обновляет элемент
    fun bindShare(post: Post) {
        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            likes.text = PostRepositoryInMemoryImpl.PostService.showValues(post.likes)
            shares.text = PostRepositoryInMemoryImpl.PostService.showValues(post.shares)
            views.text = PostRepositoryInMemoryImpl.PostService.showValues(post.views)
            share.setOnClickListener {
                onShareListener(post)
            }
        }
    }
}