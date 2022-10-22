package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.PostViewHolder
import ru.netology.nmedia.databinding.FragmentNewPostBinding
import ru.netology.nmedia.databinding.FragmentViewPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepositoryInMemoryImpl
import ru.netology.nmedia.util.StringArg
import ru.netology.nmedia.viewmodel.PostViewModel

class ViewPostFragment : Fragment() {
    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    companion object {
        private const val POST = "post"
        //var Bundle.textArg: String? by StringArg
        fun createArguments(post: Post): Bundle {
            return bundleOf(POST to post)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentViewPostBinding.inflate(
            inflater,
            container,
            false
        )
        //получаем данные поста
        var post = requireArguments().get(Companion.POST) as Post

        //функция обновления данных view
        fun updateView(): Unit {
            //заполняем view полученными данными
            binding.like.isChecked = post.likedByMe
            binding.author.setText(post.author)
            binding.content.setText(post.content)
            binding.like.setText(PostRepositoryInMemoryImpl.PostService.showValues(post.likes))
            binding.share.setText(PostRepositoryInMemoryImpl.PostService.showValues(post.shares))
            if (!post.video.isEmpty()) {
                binding.video.visibility = View.VISIBLE
                binding.play.visibility = View.VISIBLE
            } else {
                binding.video.visibility = View.GONE
                binding.play.visibility = View.GONE
            }
        }
        updateView()

        //нажатие на кнопку лайк
        binding.like.setOnClickListener {
            viewModel.likeById(post.id)
            //обновляем поле post для обновления данных
            post = viewModel.data.value?.filter {
                it.id == post.id
            }?.get(0) as Post
            //меняем view в соотвтетствии  с измененными данными поста
            updateView()
        }
        //нажатие на кнопку поделиться
                binding.share.setOnClickListener {
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
                    //обновляем поле post для обновления данных
                    post = viewModel.data.value?.filter {
                        it.id == post.id
                    }?.get(0) as Post
                    //меняем view в соотвтетствии  с измененными данными поста
                    updateView()
                }
        //обработка перехода видео
        binding.video.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.video))
            val videoIntent = Intent.createChooser(intent, "Video")
            startActivity(videoIntent)
        }
        //обработка нажатий на меню
        binding.menu.setOnClickListener {
            PopupMenu(it.context, it).apply {
                inflate(R.menu.options_post)
                setOnMenuItemClickListener {item ->
                    when (item.itemId) {
                        R.id.remove -> {
                            viewModel.removeById(post.id)
                            findNavController().navigateUp()
                            true
                        }
                        R.id.edit -> {
                            findNavController().navigate(R.id.action_viewPostFragment_to_editPostFragment, EditPostFragment.Companion.createArguments(post.content))
                            viewModel.edit(post)

                            true
                        }
                        else -> false
                    }

                }
            }.show()
        }

        return binding.root
    }
}





















/*
// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ViewPostFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ViewPostFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_post, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ViewPostFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ViewPostFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}*/