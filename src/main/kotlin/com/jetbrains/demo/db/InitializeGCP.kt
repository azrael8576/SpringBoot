package com.jetbrains.demo.db


/**
 * Created by AlexHe on 2019-02-15.
 * Describe
 */

import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.firestore.Firestore

import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.cloud.FirestoreClient
import org.springframework.core.convert.TypeDescriptor.collection
import com.google.cloud.firestore.QuerySnapshot
import com.google.api.core.ApiFuture


class InitializeGCP {
    // Use the application default credentials
    var credentials = GoogleCredentials.getApplicationDefault()
    val init =
            FirebaseApp.initializeApp(FirebaseOptions.Builder()
                    .setCredentials(credentials)
                    .setProjectId("springboot-231804")
                    .build())

    var db = FirestoreClient.getFirestore()

    // asynchronously retrieve all users
    var query = db.collection("ChatBot").get()
    // query.get() blocks on response
    var querySnapshot = query.get()
    var documents = querySnapshot.getDocuments()
    fun println () {
        for (document in documents){
            println(document.id)
        }
    }
}
