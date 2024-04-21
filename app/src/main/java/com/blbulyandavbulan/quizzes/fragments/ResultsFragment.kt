package com.blbulyandavbulan.quizzes.fragments

import android.animation.AnimatorInflater
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.blbulyandavbulan.quizzes.R
import com.blbulyandavbulan.quizzes.databinding.FragmentResultsBinding

private const val RESULTS_PARAM = "RESULTS"

/**
 * A simple [Fragment] subclass.
 * Use the [ResultsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ResultsFragment : Fragment() {
    private var results: String? = null
    private var _binding: FragmentResultsBinding? = null
    private val binding: FragmentResultsBinding
        get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            results = it.getString(RESULTS_PARAM)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentResultsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        results?.let {
            binding.resultsTextView.text = it
        }
        binding.startAgainButton.setOnClickListener {
            findNavController().popBackStack()
        }
        requireActivity().onBackPressedDispatcher.addCallback (this){
            findNavController().navigate(R.id.action_ResultsFragment_To_GreetingFragment)
        }
        AnimatorInflater.loadAnimator(context, R.animator.left_right_rotation).apply {
            setTarget(binding.startAgainButton)
            start()
        }
        binding.resultsTextScrollView.alpha = 0f
        binding.resultsTextScrollView.animate().apply {
            duration = 500
            alpha(1.0f)
        }.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(results: String) =
            ResultsFragment().apply {
                arguments = Bundle().apply {
                    putString(RESULTS_PARAM, results)
                }
            }
    }
}