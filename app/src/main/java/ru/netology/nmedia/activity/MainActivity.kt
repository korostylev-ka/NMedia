package ru.netology.nmedia.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.launch
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Group
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.databinding.ActivityEditPostBinding
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.databinding.ActivityNewPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel

var editText = ""
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        val bindingPost = ActivityEditPostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewModel: PostViewModel by viewModels()
        //группа, содержащая текущеее значение контента(текст + кнопка очистки)
        val group = findViewById<Group>(R.id.group_old)
        //строка, содержащая текущеее значение контента
        val textOld = findViewById<EditText>(R.id.content_edit)

        //регистрируем контракт? 2-й аргумент - обработчик полученного результата(текста поста)
        val activityLauncher = registerForActivityResult(NewPostActivity.Contract) {text ->
            text ?: return@registerForActivityResult
            viewModel.changeContent(text)
            viewModel.save()
        }
        val editLauncher = registerForActivityResult(EditPostActivity.ContractEdit) {text ->
            text ?: return@registerForActivityResult
            viewModel.changeContent(text)
            viewModel.save()
        }
        //по нажатию на кнопку add
        binding.add.setOnClickListener {
            //вызываем контракт по переменной activityLauncher
            activityLauncher.launch()
        }

        //создаем адаптер
        val adapter = PostAdapter(object : OnInteractionListener {

            override fun edit(post: Post) {
                viewModel.edit(post)
                editLauncher.launch()
                editText = post.content

            }

            override fun remove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun like(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun share(post: Post) {
                //интент на отправку текста
                val intent = Intent().apply{
                    action = Intent.ACTION_SEND
                    //ключ-значение
                    putExtra(Intent.EXTRA_TEXT, post.content)
                    type = "text/plain"
                }
                //интент на показ chooser а. Можно и просто intent,но будет проще функционал
                val shareIntent = Intent.createChooser(intent, getString(R.string.chooser_share_post))
                startActivity(shareIntent)
                viewModel.shareById(post.id)
            }
            //интент на просмотр видео
            override fun openVideo(post: Post) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.video))
                intent.setDataAndType(Uri.parse(post.video), "text/plain")
                val videoIntent = Intent.createChooser(intent, "Video")
                startActivity(videoIntent)
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
            with(bindingPost.contentEdit) {
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