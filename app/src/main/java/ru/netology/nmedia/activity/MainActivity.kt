package ru.netology.nmedia.activity

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Group
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val viewModel: PostViewModel by viewModels()
        //группа, содержащая текущеее значение контента(текст + кнопка очистки)
        val group = findViewById<Group>(R.id.group_old)
        //строка, содержащая текущеее значение контента
        val textOld = findViewById<EditText>(R.id.content_old)
        //по нажатию на кнопку send
        binding.buttonSend.setOnClickListener {
            with (binding.content) {
                if (text.isNullOrBlank()) {
                    Toast.makeText(
                        this@MainActivity,
                        context.getString(R.string.error_empty_content),
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                viewModel.changeContent(text.toString())
                viewModel.save()
                //очищаем binding content
                setText("")
                clearFocus()
                AndroidUtils.hideKeyboard(this)
            }

        }
        //нажатие на кнопку отмены редактирования
        binding.buttonCancel.setOnClickListener {
            //делаем группу невидимой
            group.visibility = View.INVISIBLE
            viewModel.save()
            binding.content.setText("")
            binding.content.clearFocus()
        }

        //создаем адаптер
        val adapter = PostAdapter(object : OnInteractionListener {
            override fun edit(post: Post) {
                textOld.setText(post.content)
                //делаем видимую группу
                group.visibility = View.VISIBLE
                viewModel.edit(post)
            }

            override fun remove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun like(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun share(post: Post) {
                viewModel.shareById(post.id)
            }
        })
        binding.lists.adapter = adapter
        viewModel.data.observe(this) {posts ->
            adapter.submitList(posts)
        }
        //нужно сфокусироваться на поле ввода
        viewModel.edited.observe(this) {
            if (it.id == 0L) {
                return@observe
            }
            with(binding.content) {
                setText(it.content)
                requestFocus()
            }

        }
    }

}

object AndroidUtils {
    fun hideKeyboard(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken,0)
    }
}