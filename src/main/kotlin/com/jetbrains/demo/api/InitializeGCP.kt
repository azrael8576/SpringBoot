package com.jetbrains.demo.api


/**
 * Created by AlexHe on 2019-02-15.
 * Describe
 */

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions


class InitializeGCP {
    // Use the application default credentials
    var credentials =
            GoogleCredentials.getApplicationDefault()
    val firebaseOptions =
            FirebaseOptions.Builder()
                    .setCredentials(credentials)
                    .setProjectId("springboot-232303")
                    .build()
    var myApp = FirebaseApp.initializeApp(firebaseOptions)
}
