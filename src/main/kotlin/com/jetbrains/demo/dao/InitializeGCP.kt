package com.jetbrains.demo.dao


/**
 * Created by AlexHe on 2019-02-15.
 * Describe
 */

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions


class InitializeGCP {
    fun init() {
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
    fun ckeckHasInited(hasBeenInitialized : Boolean) {
        var checkInit = hasBeenInitialized
        val firebaseApps = FirebaseApp.getApps()
        for (app in firebaseApps) {
            if (app.name == FirebaseApp.DEFAULT_APP_NAME) {
                checkInit = true
            }
        }
        if(!checkInit) {
            InitializeGCP().init()
        }
    }
}
