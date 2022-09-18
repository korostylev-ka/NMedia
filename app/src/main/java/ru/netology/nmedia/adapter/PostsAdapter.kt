package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post


//реализация адаптера
class PostAdapter(
    //слушатель
    private val onLikeListener: (Post) -> Unit,
): ListAdapter<Post, PostViewHolder>(PostDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder =
        PostViewHolder(
            /*1 параметр - нет активити, есть recyclerView(parent), из него можно взять context, а из context уже layoutInflater,
            2- сам RecyclerView, 3 - false, т.к RecyclerView сам будет решать, когда добавить*/
            CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onLikeListener
        )

    //связывает views с содержимым
    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class PostDiffCallback: DiffUtil.ItemCallback<Post>() {
    //сравнивает два поста
    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean = oldItem.id == newItem.id
    //совпадает ли контент
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean = oldItem == newItem

}

class PostAdapterShare(
    //слушатель
    private val onShareListener: (Post) -> Unit,
): ListAdapter<Post, PostViewHolderShare>(PostDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolderShare =
        PostViewHolderShare(
            CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onShareListener
        )
    //связывает views с содержимым
    override fun onBindViewHolder(holder: PostViewHolderShare, position: Int) {
        holder.bindShare(getItem(position))
    }


}




