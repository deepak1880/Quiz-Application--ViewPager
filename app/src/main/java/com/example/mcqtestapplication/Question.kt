package com.example.mcqtestapplication

import java.io.Serializable

data class Question(
    val questionText: String,
    val options: List<String>,
    var selectedOption: Int = -1 // -1 indicates no selection
) : Serializable
