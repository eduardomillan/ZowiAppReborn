package com.bq.zowi.api

import com.bq.zowi.models.Project
import io.reactivex.Single

interface ProjectController {
    companion object {
        const val PROJECT_QUIZ_BLOCKADE_MILLIS_ON_FAILURE = 600000L
    }

    fun blockProjectQuiz(projectId: String): Single<Void>
    fun getProject(projectId: String): Single<Project>
    fun getProjectIsCompleted(projectId: String): Single<Boolean>
    fun isProjectQuizBlocked(projectId: String): Single<Long>
    fun resetAllProjects(): Single<Void>
    fun setProjectCompleted(projectId: String): Single<Void>
}
