package com.blbulyandavbulan.quizzes.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.blbulyandavbulan.quizzes.R
import com.blbulyandavbulan.quizzes.databinding.FragmentGreetingBinding
import com.blbulyandavbulan.quizzes.quiz.QuizStorage

class GreetingFragment : Fragment() {
    private var _binding: FragmentGreetingBinding? = null
    private val binding: FragmentGreetingBinding
        get() = _binding!!
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGreetingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.continueButton.setOnClickListener {
            findNavController().navigate(R.id.action_GreetingFragment_To_QuizFragment, Bundle().apply {
                val locale = QuizStorage.Locale.Ru // FIXME: добавить здесь корректное получение локали
                putSerializable("QUIZ", QuizStorage.getQuiz(locale))
            });
        }
    }
}