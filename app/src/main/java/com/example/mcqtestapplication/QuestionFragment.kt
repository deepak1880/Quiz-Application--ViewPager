package com.example.mcqtestapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import com.example.mcqtestapplication.databinding.FragmentQuestionBinding

private const val ARG_POSITION = "position"
private const val ARG_QUESTION = "question"

// Fragment to display a single question
class QuestionFragment : Fragment() {

    private var position: Int = 0
    private var question: Question? = null
    private var _binding: FragmentQuestionBinding? = null
    private val binding get() = _binding!!

    companion object {
        // Factory method to create a new instance of this fragment
        fun newInstance(position: Int, question: Question) = QuestionFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_POSITION, position)
                putSerializable(ARG_QUESTION, question)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            position = it.getInt(ARG_POSITION)
            question = it.getSerializable(ARG_QUESTION) as Question
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQuestionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set question text
        binding.tvQuestion.text = question?.questionText

        // Dynamically add radio buttons for each option
        question?.options?.forEachIndexed { index, option ->
            val radioButton = RadioButton(context)
            radioButton.id = index
            radioButton.text = option
            binding.radioGroupOptions.addView(radioButton)
        }

        // Restore selected option if previously selected
        question?.selectedOption?.let { selected ->
            if (selected != -1) {
                binding.radioGroupOptions.check(selected)
            }
        }

        // Listener to save selected option
        binding.radioGroupOptions.setOnCheckedChangeListener { group, checkedId ->
            question?.selectedOption = checkedId
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Prevent memory leaks
    }
}
