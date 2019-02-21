package com.jetbrains.demo

import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.firestore.*
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.cloud.FirestoreClient
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.HashMap

import com.jetbrains.demo.api.InitializeGCP


/**
 * Created by AlexHe on 2019-02-18.
 * Describe
 */


//data class Text(val userId: String = "Null", val platform: Long = 0, val city: String = "台北市", val time: String = "08:00")
data class Text(val text: String)

@RestController
class TextController {
    @RequestMapping("/text")
    fun text(): Text {

        // Use the application default credentials
        var hasBeenInitialized = false
        val firebaseApps = FirebaseApp.getApps()
        for (app in firebaseApps) {
            if (app.name == FirebaseApp.DEFAULT_APP_NAME) {
                hasBeenInitialized = true

            }
        }
        if(!hasBeenInitialized) {
            InitializeGCP()
        }


        var db = FirestoreClient.getFirestore()
        var temp = ""

        val query = db.collection("subscription").get()
        val querySnapshot = query.get()
        val documents = querySnapshot.documents

        for (document in documents) {
            temp += ("city: " + document.getString("city")!!)
            temp += ("platform: " + document.getString("platform")!!)
            temp += ("time: " + document.getString("time")!!)
            temp += ("userId: " + document.getString("userId")!!)
        }

        return Text("All data: $temp")
    }

}
