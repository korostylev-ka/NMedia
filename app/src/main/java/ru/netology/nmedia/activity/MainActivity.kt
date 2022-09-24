package ru.netology.nmedia.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewModel: PostViewModel by viewModels()
        //создаем адаптер
        val adapter = PostAdapter(
            onLikeListener = {
                viewModel.likeById(it.id)
            },
            onShareListener = {
                viewModel.shareById(it.id)
            }
        )
        binding.lists.adapter = adapter
        viewModel.data.observe(this) {posts ->
            adapter.submitList(posts)
        }
    }




}