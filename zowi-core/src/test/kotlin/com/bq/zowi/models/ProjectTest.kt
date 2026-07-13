package com.bq.zowi.models

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ProjectTest {

    private fun createProject(
        id: String = "proj1",
        titleResourceId: String = "title_1",
        learningDescriptionResourceId: String = "desc_1",
        imageResourceId: String = "img_1",
        projectUrlResourceId: String = "url_1",
        testQuestions: ArrayList<Project.TestQuestion> = arrayListOf(),
        achievementId: String = "ach_1",
        projectHex: String? = null
    ) = Project(
        id, titleResourceId, learningDescriptionResourceId,
        imageResourceId, projectUrlResourceId,
        testQuestions, achievementId, projectHex
    )

    @Test
    fun constructorSetsAllFields() {
        val project = createProject(
            id = "p1",
            titleResourceId = "title",
            learningDescriptionResourceId = "learn",
            imageResourceId = "image",
            projectUrlResourceId = "url",
            achievementId = "ach",
            projectHex = "0xABC"
        )

        assertEquals("p1", project.id)
        assertEquals("title", project.titleResourceId)
        assertEquals("learn", project.learningDescriptionResourceId)
        assertEquals("image", project.imageResourceId)
        assertEquals("url", project.projectUrlResourceId)
        assertEquals("ach", project.achievementId)
        assertEquals("0xABC", project.projectHex)
    }

    @Test
    fun constructorWithNullProjectHex() {
        val project = createProject()
        assertNull(project.projectHex)
    }

    @Test
    fun defaultMutableFieldsAreFalseOrZero() {
        val project = createProject()

        assertEquals(0L, project.blockadePendingMillis)
        assertEquals(false, project.isCompleted)
        assertEquals(false, project.isQuizBlocked)
    }

    @Test
    fun mutableFieldsAreSettable() {
        val project = createProject()

        project.blockadePendingMillis = 5000L
        project.isCompleted = true
        project.isQuizBlocked = true

        assertEquals(5000L, project.blockadePendingMillis)
        assertEquals(true, project.isCompleted)
        assertEquals(true, project.isQuizBlocked)
    }

    @Test
    fun equalProjectsAreEqual() {
        val questions = arrayListOf(
            Project.TestQuestion("q1", arrayListOf(Project.TestAnswer("a1", true)))
        )
        val p1 = createProject(id = "1", titleResourceId = "t", testQuestions = questions)
        val p2 = createProject(id = "1", titleResourceId = "t", testQuestions = questions)

        assertEquals(p1, p2)
    }

    @Test
    fun differentProjectsAreNotEqual() {
        val p1 = createProject(id = "1")
        val p2 = createProject(id = "2")

        assertNotEquals(p1, p2)
    }

    @Test
    fun equalProjectsHaveSameHashCode() {
        val p1 = createProject(id = "1", titleResourceId = "t")
        val p2 = createProject(id = "1", titleResourceId = "t")

        assertEquals(p1.hashCode(), p2.hashCode())
    }

    @Test
    fun differentProjectsHaveDifferentHashCodes() {
        val p1 = createProject(id = "1", titleResourceId = "t1")
        val p2 = createProject(id = "2", titleResourceId = "t2")

        assertNotEquals(p1.hashCode(), p2.hashCode())
    }

    @Test
    fun copyProducesEqualProject() {
        val original = createProject(id = "p1", projectHex = "0xFF")
        val copied = original.copy()

        assertEquals(original, copied)
    }

    @Test
    fun copyWithModifiedFieldProducesDifferentProject() {
        val original = createProject(id = "p1")
        val modified = original.copy(id = "p2")

        assertNotEquals(original, modified)
        assertEquals("p2", modified.id)
    }

    @Test
    fun testQuestionsAreStoredCorrectly() {
        val answer1 = Project.TestAnswer("ans1", true)
        val answer2 = Project.TestAnswer("ans2", false)
        val question = Project.TestQuestion("q1", arrayListOf(answer1, answer2))
        val project = createProject(testQuestions = arrayListOf(question))

        assertEquals(1, project.testQuestions.size)
        assertEquals("q1", project.testQuestions[0].questionResourceId)
        assertEquals(2, project.testQuestions[0].answers.size)
        assertEquals("ans1", project.testQuestions[0].answers[0].answerResourceId)
        assertEquals(true, project.testQuestions[0].answers[0].isCorrect)
        assertEquals("ans2", project.testQuestions[0].answers[1].answerResourceId)
        assertEquals(false, project.testQuestions[0].answers[1].isCorrect)
    }

    @Test
    fun emptyTestQuestionsByDefault() {
        val project = createProject()
        assertTrue(project.testQuestions.isEmpty())
    }
}
