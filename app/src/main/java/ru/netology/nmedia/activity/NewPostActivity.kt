package ru.netology.nmedia.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityNewPostBinding

class NewPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityNewPostBinding.inflate(layoutInflater)
    setContentView(binding.root)
    //обработка нажатия на ок
    binding.ok.setOnClickListener {
        val content = binding.content.text.toString()
        //если контент пустой
        if (content.isEmpty()) {
            setResult(RESULT_CANCELED)
        } else {
            //если контент не пустой, возвращаем интент с прочитанным текстом
            setResult(RESULT_OK, Intent().putExtra(Intent.EXTRA_TEXT, content))
        }
        finish()
    }
}
//класс контракта <входные, выходные данные>
object Contract: ActivityResultContract<Unit, String?>() {
    //отвечает за формирования интента для запуска активити
    override fun createIntent(context: Context, input: Unit) =
        //контекст и ссылка на запускаемую активити
        Intent(context, NewPostActivity::class.java)

    //интент на результат,  принимает на вход resultcode(RESAULT_OK) и интент
    override fun parseResult(resultCode: Int, intent: Intent?): String? =
        if (resultCode == RESULT_OK) {
            intent?.getStringExtra(Intent.EXTRA_TEXT)
        } else {
            null
        }
}

object ContractEdit: ActivityResultContract<Unit, String?>() {
    //отвечает за формирования интента для запуска активити
    override fun createIntent(context: Context, input: Unit) =
        //контекст и ссылка на запускаемую активити
        Intent(context, NewPostActivity::class.java)

    //интент на результат,  принимает на вход resultcode(RESAULT_OK) и интент
    override fun parseResult(resultCode: Int, intent: Intent?): String? =
        if (resultCode == RESULT_OK) {
            intent?.getStringExtra(Intent.ACTION_GET_CONTENT)
        } else {
            null
        }
}


}