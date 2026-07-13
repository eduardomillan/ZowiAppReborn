package com.bq.zowi.adapters

import com.bq.zowi.api.AssetProvider
import com.bq.zowi.api.KeyValueStore
import com.bq.zowi.api.ProjectController
import com.bq.zowi.models.Project
import com.google.gson.Gson
import io.reactivex.Single

class AndroidCoreProjectController(
    private val store: KeyValueStore,
    private val assetProvider: AssetProvider,
    private val gson: Gson = Gson()
) : ProjectController {

    @Suppress("UNCHECKED_CAST")
    override fun blockProjectQuiz(projectId: String): Single<Void> {
        return Single.fromCallable {
            store.putLong("${projectId}_project_quiz_blockade", System.currentTimeMillis())
            store.commit()
            null as Void
        }
    }

    override fun getProject(projectId: String): Single<Project> {
        return Single.fromCallable {
            val inputStream = assetProvider.openAsset("projects/$projectId.json")
            val text = inputStream.bufferedReader().readText()
            gson.fromJson(text, Project::class.java)
        }
    }

    override fun getProjectIsCompleted(projectId: String): Single<Boolean> {
        return Single.fromCallable {
            store.getBoolean("${projectId}_project_completeness", false)
        }
    }

    override fun isProjectQuizBlocked(projectId: String): Single<Long> {
        return Single.fromCallable {
            store.getLong("${projectId}_project_quiz_blockade", -1L)
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun resetAllProjects(): Single<Void> {
        return Single.fromCallable {
            val all = store.getAll()
            all.keys.filter { it.endsWith("_project_completeness") || it.endsWith("_project_quiz_blockade") }
                .forEach { store.remove(it) }
            store.commit()
            null as Void
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun setProjectCompleted(projectId: String): Single<Void> {
        return Single.fromCallable {
            store.putBoolean("${projectId}_project_completeness", true)
            store.commit()
            null as Void
        }
    }
}
