package com.adewan.scout.core.preference

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class PreferenceRepository(private val store: FirebaseFirestore, private val auth: FirebaseAuth) {

    suspend fun getUserPreferredGenres(): List<Int> {
        val uid = auth.currentUser?.uid ?: return emptyList()
        return try {
            val document =
                store.collection("user").document(uid).collection("data").document("genres").get()
                    .await()
            document.data?.get("genres") as? List<Int> ?: emptyList()
        } catch (e: Exception) {
            println("Error getting user preferred genres: ${e.localizedMessage}")
            emptyList()
        }
    }

    suspend fun setUserPreferredGenres(genres: List<Int>) {
        val uid = auth.currentUser?.uid ?: return
        try {
            store.collection("user").document(uid).collection("data").document("genres")
                .set(mapOf("genres" to genres)).await()
        } catch (e: Exception) {
            println("Error setting user preferred genres: ${e.localizedMessage}")
        }
    }

    suspend fun getUserPreferredPlatforms(): List<Int> {
        val uid = auth.currentUser?.uid ?: return emptyList()
        return try {
            val document =
                store.collection("user").document(uid).collection("data").document("platforms")
                    .get().await()
            document.data?.get("platforms") as? List<Int> ?: emptyList()
        } catch (e: Exception) {
            println("Error getting user preferred platforms: ${e.localizedMessage}")
            emptyList()
        }
    }

    suspend fun setUserPreferredPlatforms(platforms: List<Int>) {
        val uid = auth.currentUser?.uid ?: return
        try {
            store.collection("user").document(uid).collection("data").document("platforms")
                .set(mapOf("platforms" to platforms)).await()
        } catch (e: Exception) {
            println("Error setting user preferred platforms: ${e.localizedMessage}")
        }
    }
}