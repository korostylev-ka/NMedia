//класс принимает на вход view и нужен, чтобы держать на нее ссылку, чтобы использовать для обновления элементов списка

package ru.netology.nmedia.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.constraintlayout.widget.Group
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepositoryInMemoryImpl

interface OnInteractionListener {
    fun edit(post: Post)
    fun like(post: Post)
    fun remove(post: Post)
    fun share(post: Post)
    fun openVideo(post: Post)
    fun openPost(post: Post)

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
            like.text = PostRepositoryInMemoryImpl.PostService.showValues(post.likes)
            share.text = PostRepositoryInMemoryImpl.PostService.showValues(post.shares)
            views.text = PostRepositoryInMemoryImpl.PostService.showValues(post.views)
            like.isChecked = post.likedByMe
            //если есть ссылка на видео, то отображать поле, если нет, то скрыть и не занмать место
            if (!post.video.isEmpty()) {
                binding.video.visibility = View.VISIBLE
                binding.play.visibility = View.VISIBLE
            } else {
                binding.video.visibility = View.GONE
                binding.play.visibility = View.GONE
            }
            video.setOnClickListener {
                listener.openVideo(post)
            }
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
            content.setOnClickListener {
                listener.openPost(post)
            }

        }
    }

}

