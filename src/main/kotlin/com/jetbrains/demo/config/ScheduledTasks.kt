package com.jetbrains.demo.config

import com.google.firebase.FirebaseApp
import com.google.firebase.cloud.FirestoreClient
import com.jetbrains.demo.Text
import com.jetbrains.demo.api.InitializeGCP
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.postForEntity
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import org.springframework.util.MultiValueMap
import java.time.Instant
import java.util.HashMap






/**
 * Created by AlexHe on 2019-02-19.
 * Describe
 */
class ScheduledTasks {

    @Scheduled(cron = "0 30/5 * * * *")
    fun subscription() {
        val df = DateTimeFormatter.ofPattern("HH:mm")
        var currentDateTime = LocalDateTime.now().plusHours(8)
        var localTime = df.format(currentDateTime)
        println(currentDateTime)
        println("現在時間$localTime")
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
        val query = db.collection("subscription").get()
        val querySnapshot = query.get()
        val documents = querySnapshot.documents

        for (document in documents) {
            //localTime.equals(document.getString("time"))
            if (localTime.equals(document.getString("time"))) {
                println("一次post time$currentDateTime")
                var restTemplate = RestTemplate()
                var headers = HttpHeaders()
                var type = MediaType.parseMediaType("application/json; charset=UTF-8")
                headers.setContentType(type)
                val url = "https://linebot-225214.appspot.com/post"

                val requestJson = "{\"userId\":\"${document.getString("userId")}\",\n" +
                        "\t\"platform\":\"${document.getLong("platform")}\",\n" +
                        "\t\"city\":\"${document.getString("city")}\"}"

                var entity = HttpEntity<String>(requestJson,headers)
                var result = restTemplate.postForObject(url, entity, String::class.java)
                println("一次post end!$currentDateTime")
                println(result)
            }
        }
    }
}
