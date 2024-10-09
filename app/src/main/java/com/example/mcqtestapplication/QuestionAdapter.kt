package com.example.mcqtestapplication

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

// Adapter for ViewPager2 to manage QuestionFragments
class QuestionAdapter(
    fragmentActivity: FragmentActivity,
    private val questions: List<Question>
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = questions.size

    override fun createFragment(position: Int): Fragment {
        // Pass the question data to the fragment
        return QuestionFragment.newInstance(position, questions[position])
    }
}
