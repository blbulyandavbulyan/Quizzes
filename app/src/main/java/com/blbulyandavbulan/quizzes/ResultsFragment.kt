package com.blbulyandavbulan.quizzes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blbulyandavbulan.quizzes.databinding.FragmentResultsBinding

private const val RESULTS_PARAM = "RESULTS"

/**
 * A simple [Fragment] subclass.
 * Use the [ResultsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ResultsFragment : Fragment() {
    // TODO: Rename and change types of parameters
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param results Parameter 1.
         * @return A new instance of fragment ResultsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(results: String) =
            ResultsFragment().apply {
                arguments = Bundle().apply {
                    putString(RESULTS_PARAM, results)
                }
            }
    }
}