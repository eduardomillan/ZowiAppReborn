package com.bq.zowi.models

data class Project(
    val id: String,
    val titleResourceId: String,
    val learningDescriptionResourceId: String,
    val imageResourceId: String,
    val projectUrlResourceId: String,
    val testQuestions: ArrayList<TestQuestion>,
    val achievementId: String,
    val projectHex: String? = null
) {
    var blockadePendingMillis: Long = 0
    var isCompleted: Boolean = false
    var isQuizBlocked: Boolean = false

    data class TestQuestion(
        val questionResourceId: String,
        val answers: ArrayList<TestAnswer>
    )

    data class TestAnswer(
        val answerResourceId: String,
        val isCorrect: Boolean
    )
}
