package ru.netology.nmedia.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityIntentHandlerBinding

class IntentHandlerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityIntentHandlerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //проверяем action
        intent?.let{
            if (it.action != Intent.ACTION_SEND) {
                return@let
            }
            //берем строковые данные по ключу EXTRA_TEXT
            val text = it.getStringExtra(Intent.EXTRA_TEXT)
            if (text.isNullOrBlank()) {
                //Создаем уведомление(view, сообщение, продолжительность)
                Snackbar.make(binding.root, R.string.error_empty_content, Snackbar.LENGTH_INDEFINITE)
                    //создаем кнопку ок, при нажатии на которую идет завершение
                    .setAction(android.R.string.ok) {
                        finish()
                    }
                    .show()
                return@let
            }
            Toast.makeText(this,text,Toast.LENGTH_LONG).show()

        }
    }
}