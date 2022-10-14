package ru.netology.nmedia.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityEditPostBinding
import ru.netology.nmedia.databinding.ActivityNewPostBinding

class EditPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityEditPostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val text = intent?.getStringExtra(Intent.EXTRA_TITLE)
        binding.contentEdit.setText(text)
        //обработка нажатия на ок
        binding.ok.setOnClickListener {
            val content = binding.contentEdit.text.toString()
            //если контент пустой
            if (content.isEmpty()) {
                setResult(RESULT_CANCELED)
            } else {
                //если контент не пустой, возвращаем интент с прочитанным текстом
                setResult(RESULT_OK, Intent().putExtra(Intent.EXTRA_TITLE, content))
            }
            finish()
        }
    }

    object ContractEdit: ActivityResultContract<String, String?>() {
        //отвечает за формирования интента для запуска активити, на входе String(поле контента)
        override fun createIntent(context: Context, input: String) =
            //контекст и ссыval int = EditPostActivity.ContractEdit.createIntent(this@MainActivity, post.content)лка на запускаемую активити
            Intent(context, EditPostActivity::class.java)
                //кладем с ключем EXTRA_TITLE значение текущего контента
                .putExtra(Intent.EXTRA_TITLE, input)

        //интент на результат,  принимает на вход resultcode(RESAULT_OK) и интент
        override fun parseResult(resultCode: Int, intent: Intent?): String? =
            if (resultCode == RESULT_OK) {
                intent?.getStringExtra(Intent.EXTRA_TITLE)
            } else {
                null
            }
    }
}