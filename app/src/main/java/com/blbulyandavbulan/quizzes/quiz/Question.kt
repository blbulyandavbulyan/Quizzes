package com.blbulyandavbulan.quizzes.quiz

import java.io.Serializable

class Question(
    val question:String,
    val answers:List<String>,
    val feedback:List<String>,
): Serializable