package com.blbulyandavbulan.quizzes.fragments

import android.graphics.Typeface
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.blbulyandavbulan.quizzes.R
import com.blbulyandavbulan.quizzes.databinding.FragmentQuizBinding
import com.blbulyandavbulan.quizzes.quiz.Quiz
import com.blbulyandavbulan.quizzes.quiz.QuizStorage
import com.google.android.material.radiobutton.MaterialRadioButton
import java.util.stream.Collectors

private const val QUIZ_PARAM = "QUIZ"

/**
 * A simple [Fragment] subclass.
 * Use the [QuizFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class QuizFragment : Fragment() {
    private var quiz: Quiz? = null
    private var _binding: FragmentQuizBinding? = null
    private val binding: FragmentQuizBinding
        get() = _binding!!
    private var answersRadioGroups: List<RadioGroup>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            //я знаю что этот метод depracated, однако для использования альтернативы нужно повысить уровень minSdk
            // чего я пока делать не хочу
            quiz = it.getSerializable(QUIZ_PARAM) as Quiz
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        binding.quizzesLinearLayout
        val answersRadioGroups = mutableListOf<RadioGroup>()
        this.answersRadioGroups = answersRadioGroups
        quiz?.questions?.forEach { question ->
            val context = requireContext()
            val questionTextView = TextView(context)
            questionTextView.text = question.question
            questionTextView.id = View.generateViewId()
            questionTextView.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
            questionTextView.gravity = Gravity.CENTER
            questionTextView.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            questionTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
            binding.quizzesLinearLayout.addView(questionTextView)
            val radioGroup = RadioGroup(context)
            question.answers.forEach { answer ->
                val answerRadioButton = MaterialRadioButton(context)
                answerRadioButton.text = answer
                answerRadioButton.id = View.generateViewId()
                answerRadioButton.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                radioGroup.addView(answerRadioButton)
                radioGroup.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            }
            binding.quizzesLinearLayout.addView(radioGroup)
            radioGroup.check(radioGroup.getChildAt(0).id)
            answersRadioGroups.add(radioGroup)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.submitAnswers.setOnClickListener { _ ->
            answersRadioGroups?.stream()
                ?.map { it.indexOfChild(it.findViewById(it.checkedRadioButtonId)) }
                ?.collect(Collectors.toList())?.let {answers->
                    quiz?.let {
                        val answer = QuizStorage.answer(it, answers)
                        findNavController().navigate(R.id.action_QuizFragment_To_ResultsFragment, Bundle().apply {
                            putString("RESULTS", answer)
                        })
                    }
                }
        }
        binding.goBackButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(quiz: Quiz) =
            QuizFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(QUIZ_PARAM, quiz)
                }
            }
    }
}