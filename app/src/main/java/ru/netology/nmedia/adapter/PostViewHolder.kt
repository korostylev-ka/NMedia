//класс принимает на вход view и нужен, чтобы держать на нее ссылку, чтобы использовать для обновления элементов списка

package ru.netology.nmedia.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.constraintlayout.widget.Group
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepositoryInMemoryImpl

interface OnInteractionListener {
    fun edit(post: Post)
    fun like(post: Post)
    fun remove(post: Post)
    fun share(post: Post)

}

class PostViewHolder(
    private val binding: CardPostBinding,
    private val listener: OnInteractionListener
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
                listener.like(post)
            }
            share.setOnClickListener {
                listener.share(post)
            }
            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.options_post)
                    setOnMenuItemClickListener {item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                listener.remove(post)
                                true
                            }
                            R.id.edit -> {
                                listener.edit(post)
                                true
                            }
                            else -> false
                        }

                    }
                }.show()
            }

        }
    }

}

