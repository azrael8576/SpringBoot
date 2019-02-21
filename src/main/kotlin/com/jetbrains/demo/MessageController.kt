package com.jetbrains.demo

import org.springframework.web.bind.annotation.*

import com.google.auth.oauth2.GoogleCredentials

import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.cloud.FirestoreClient
import com.google.api.core.ApiFuture
import java.util.HashMap
import org.springframework.core.convert.TypeDescriptor.collection
import com.google.cloud.firestore.DocumentReference
import com.google.cloud.firestore.Firestore
import com.google.cloud.firestore.QueryDocumentSnapshot
import com.google.cloud.firestore.QuerySnapshot
import org.springframework.core.convert.TypeDescriptor.collection

import com.jetbrains.demo.api.InitializeGCP



/*

data class Message(val text: String)

@RestController
class MessageController {
    @RequestMapping("/message")
    fun message(): Message {

        // Use the application default credentials
        var credentials = GoogleCredentials.getApplicationDefault()
        val init = FirebaseApp.getInstance()
                FirebaseApp.initializeApp(FirebaseOptions.Builder()
                        .setCredentials(credentials)
                        .setProjectId("springboot-232107")
                        .build())

        var db = FirestoreClient.getFirestore()

        //add data
//        init.delete()
//
//        return addData(api)
        // asynchronously retrieve all users
        val query = db.collection("subscription").get()
// ...
// query.get() blocks on response
        val querySnapshot = query.get()
        val documents = querySnapshot.documents

        var temp = ""

        for (document in documents) {
            temp += ("User: " + document.id)
            temp += ("First: " + document.getString("first")!!)
            if (document.contains("middle")) {
                temp += ("Middle: " + document.getString("middle")!!)
            }
            temp += ("Last: " + document.getString("last")!!)
            temp += ("Born: " + document.getLong("born")!!)
        }
        init.delete()
        return Message("FUCK: $temp")
    }

    private fun addData(db: Firestore): Message {
        val docRef = db.collection("users").document("alovelace")
        // Add document data  with id "alovelace" using a hashmap
        val data = HashMap<String, Any>()
        data["first"] = "Ada"
        data["last"] = "Lovelace"
        data["born"] = 1815
        //asynchronously write data
        val result = docRef.set(data)
        // ...
        // com.jetbrains.demo.result.get() blocks on response

        return Message("Update time : ")
    }

}
*/
