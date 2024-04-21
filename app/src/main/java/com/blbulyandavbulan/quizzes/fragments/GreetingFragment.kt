package com.blbulyandavbulan.quizzes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.blbulyandavbulan.quizzes.R
import com.blbulyandavbulan.quizzes.databinding.FragmentGreetingBinding
import com.blbulyandavbulan.quizzes.quiz.QuizStorage
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import java.time.Instant
import java.util.TimeZone

class GreetingFragment : Fragment() {
    private var selectedDateOfBirth: Long? = null
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
                putSerializable("QUIZ", QuizStorage.getQuiz())
            });
        }

        binding.selectDateOfBirthButton.setOnClickListener { _ ->
            val dataPicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText(R.string.select_date_of_birth)
                .apply {
                    selectedDateOfBirth?.let {
                        setSelection(selectedDateOfBirth)
                    }
                }.build()
            dataPicker.addOnPositiveButtonClickListener {
                this.view?.let { view ->
                    Snackbar.make(view, Instant.ofEpochMilli(it).atZone(TimeZone.getDefault().toZoneId()).toLocalDate().toString(), Snackbar.LENGTH_SHORT).show()
                    binding.continueButton.isEnabled = true
                }
            }
            fragmentManager?.let {
                dataPicker.show(it, "DataPicker")
            }

        }
    }
}