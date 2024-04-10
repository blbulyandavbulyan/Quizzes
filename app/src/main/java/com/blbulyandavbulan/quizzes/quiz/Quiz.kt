package com.blbulyandavbulan.quizzes.quiz

import java.io.Serializable

interface Quiz : Serializable{
    val questions: List<Question>
}
