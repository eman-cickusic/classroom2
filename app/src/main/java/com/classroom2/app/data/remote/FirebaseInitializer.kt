package com.classroom2.app.data.remote

import android.content.Context
import android.util.Log
import com.google.firebase.FirebaseApp

object FirebaseInitializer {

    private const val TAG = "FirebaseInitializer"

    /** True when a real, non-placeholder Firebase project is wired up. */
    var firestoreAvailable: Boolean = false
        private set

    fun init(context: Context) {
        firestoreAvailable = try {
            val app = FirebaseApp.initializeApp(context)
                ?: FirebaseApp.getInstance()
            val projectId = app.options.projectId
            val real = !projectId.isNullOrBlank() &&
                projectId != FirestorePaths.PLACEHOLDER_PROJECT_ID
            Log.i(TAG, "Firebase project id: $projectId (real=$real)")
            real
        } catch (t: Throwable) {
            Log.w(TAG, "Firebase init failed; using in-memory backend", t)
            false
        }
    }
}
