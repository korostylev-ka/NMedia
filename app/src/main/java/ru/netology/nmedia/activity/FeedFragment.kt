package ru.netology.nmedia.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.launch
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.databinding.FragmentFeedBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel

class FeedFragment : Fragment() {
    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFeedBinding.inflate(
            inflater,
            container,
            false
        )

        //создаем адаптер
        val adapter = PostAdapter(object : OnInteractionListener {
            override fun openPost(post: Post) {
                findNavController().navigate(R.id.action_feedFragment_to_viewPostFragment, ViewPostFragment.Companion.createArguments(post))
                //viewModel.edit(post)
            }

            override fun edit(post: Post) {
                //передаем текущий текст поста
                findNavController().navigate(R.id.action_feedFragment_to_editPostFragment, EditPostFragment.Companion.createArguments(post.content))
                viewModel.edit(post)
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
                val videoIntent = Intent.createChooser(intent, "Video")
                startActivity(videoIntent)
            }
        })
        binding.lists.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) {posts ->
            adapter.submitList(posts)
        }
        binding.add.setOnClickListener{
            findNavController().navigate(R.id.action_feedFragment_to_newPostFragment)
        }

        //возвращаем экземпляр view
        return binding.root

    }

}

object AndroidUtils {
    fun hideKeyboard(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken,0)
    }
}