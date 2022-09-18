package ru.netology.nmedia.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.adapter.PostAdapterShare
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewModel: PostViewModel by viewModels()
        //создаем адаптер для лайка
        val adapterLike = PostAdapter{
            viewModel.likeById(it.id)
        }
        //создаем адаптер для share
        val adapterShare = PostAdapterShare {
            viewModel.shareById(it.id)
        }
        binding.lists.adapter = adapterLike
        viewModel.data.observe(this) {posts ->
            adapterLike.submitList(posts)
            //adapterShare.submitList(posts)
        }
    }




}