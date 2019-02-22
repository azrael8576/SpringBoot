package com.jetbrains.demo.Scheduled

import com.google.firebase.FirebaseApp
import com.google.firebase.cloud.FirestoreClient
import com.jetbrains.demo.dao.InitializeGCP
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


/**
 * Created by AlexHe on 2019-02-19.
 * Describe
 */
@Component
class ScheduledTasks {

    @Scheduled(cron = "0 0 * * * *")
    fun subscription() {
        var hasBeenInitialized = false
        //Ckeck Has Inited if has't Init GCP App
        InitializeGCP().ckeckHasInited(hasBeenInitialized)

        val df = DateTimeFormatter.ofPattern("HH:mm")
        var currentDateTime = LocalDateTime.now().plusHours(8)
        var localTime = df.format(currentDateTime)
        println(currentDateTime)
        println("LocalTime: $localTime")

        var db = FirestoreClient.getFirestore()
        val query = db.collection("subscription").get()
        val querySnapshot = query.get()
        val documents = querySnapshot.documents

        for (document in documents) {
            if (localTime.equals(document.getString("time"))) {
                //Post to Chat Bot Api
                println("send post: $currentDateTime")
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
                println(result)
            }
        }
    }
}