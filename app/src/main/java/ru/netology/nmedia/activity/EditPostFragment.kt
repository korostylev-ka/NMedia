package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContract
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.activity.NewPostFragment.Companion.textArg
import ru.netology.nmedia.databinding.FragmentNewPostBinding
import ru.netology.nmedia.util.StringArg
import ru.netology.nmedia.viewmodel.PostViewModel


class EditPostFragment : Fragment() {
    private val viewModel: PostViewModel by viewModels (
        ownerProducer = ::requireParentFragment
    )

    companion object {
        //переменная для передачи текущего текста поста
        private const val POST_CONTENT = "postContent"
        var Bundle.textArg: String? by StringArg
        fun createArguments(postContent: String?): Bundle {
            return bundleOf(POST_CONTENT to postContent)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNewPostBinding.inflate(
            inflater,
            container,
            false
        )

        arguments?.textArg
            ?.let(binding.edit::setText)
        //получаем текущее значение текста поста
        val text = requireArguments().getString(POST_CONTENT)
        binding.edit.setText(text)
        binding.edit.requestFocus()
        binding.ok.setOnClickListener {
            val intent = Intent()
            if (TextUtils.isEmpty(binding.edit.text)) {
                activity?.setResult(Activity.RESULT_CANCELED, intent)
            } else {
                val content = binding.edit.text.toString()
                intent.putExtra(Intent.EXTRA_TITLE,content)
                activity?.setResult(Activity.RESULT_OK, intent)
            }
            viewModel.changeContent(binding.edit.text.toString())
            viewModel.save()
            AndroidUtils.hideKeyboard(requireView())
            //делаем шаг назад после нажатия на ОК на предыдущий фрагмент
            findNavController().navigate(R.id.feedFragment)
        }
        return binding.root
    }

}