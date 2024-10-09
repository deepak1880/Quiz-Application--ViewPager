package com.example.mcqtestapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.mcqtestapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var questionAdapter: QuestionAdapter
    private val questions = mutableListOf<Question>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize questions
        initializeQuestions()

        // Set up the adapter
        questionAdapter = QuestionAdapter(this, questions)
        binding.viewPager.adapter = questionAdapter

        // Disable swipe to prevent skipping questions (optional)
        binding.viewPager.isUserInputEnabled = false

        // Set initial state of navigation buttons
        updateNavigationButtons()

        // Set click listeners using binding
        binding.btnBack.setOnClickListener {
            if (binding.viewPager.currentItem > 0) {
                binding.viewPager.currentItem -= 1
                updateNavigationButtons()
            }
        }

        binding.btnNext.setOnClickListener {
            if (binding.viewPager.currentItem < questions.size - 1) {
                binding.viewPager.currentItem += 1
                updateNavigationButtons()
            } else {
                // Last question, perform submission
                submitAnswers()
            }
        }

        // Listen for page changes to update buttons
        binding.viewPager.registerOnPageChangeCallback(object :  ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                updateNavigationButtons()
            }
        })
    }

    // Initialize the list of questions
    private fun initializeQuestions() {
        // Example questions; replace with your actual questions
        questions.add(
            Question(
                "What is the capital of France?",
                listOf("Berlin", "London", "Paris", "Madrid")
            )
        )
        questions.add(
            Question(
                "Which planet is known as the Red Planet?",
                listOf("Earth", "Mars", "Jupiter", "Saturn")
            )
        )
        // Add more questions up to 10
        for (i in 3..10) {
            questions.add(
                Question(
                    "Sample Question $i?",
                    listOf("Option A", "Option B", "Option C", "Option D")
                )
            )
        }
    }

    // Update the state of navigation buttons based on the current page
    private fun updateNavigationButtons() {
        val current = binding.viewPager.currentItem
        binding.btnBack.isEnabled = current > 0

        if (current == questions.size - 1) {
            binding.btnNext.text = "Submit"
        } else {
            binding.btnNext.text = "Next"
        }
    }

    // Handle submission of answers
    private fun submitAnswers() {
        // Check if all questions are answered
        val unanswered = questions.filter { it.selectedOption == -1 }
        if (unanswered.isNotEmpty()) {
            Toast.makeText(
                this,
                "Please answer all questions before submitting.",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        // Process the answers (e.g., calculate score)
        var score = 0
        // Example correct answers; replace with actual correct answers
        val correctAnswers = listOf(2, 1, 0, 3, 1, 2, 0, 3, 1, 2) // indices of correct options

        for (i in questions.indices) {
            if (questions[i].selectedOption == correctAnswers[i]) {
                score += 1
            }
        }

        // Display the score
        Toast.makeText(this, "You scored $score out of ${questions.size}", Toast.LENGTH_LONG).show()
    }
}