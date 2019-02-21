package com.jetbrains.demo.controller

import com.google.firebase.FirebaseApp
import com.google.firebase.cloud.FirestoreClient
import com.jetbrains.demo.api.InitializeGCP
import com.jetbrains.demo.bean.Subscription
import com.jetbrains.demo.result.Result
import com.jetbrains.demo.result.ResultStatus
import org.springframework.web.bind.annotation.*
import java.time.LocalTime
import javax.servlet.http.HttpServletResponse
import java.util.HashMap
import com.google.api.core.ApiFuture
import org.springframework.core.convert.TypeDescriptor.collection








/**
 * Created by AlexHe on 2019-02-18.
 * Describe
 */


@RestController
@RequestMapping("/subscriptions")
class SubscriptionController {
    var hasBeenInitialized = false
    //可以直接返回
    @GetMapping
    fun getAll(): Any = subscriptions

    //直接返回可省略返回类型，因为可以自动推断返回值；另外注意路径变量的写法
    @GetMapping("/{index}")
    fun get(@PathVariable index: Int) = subscriptions[index]

    //前端直接提交JSON对象，这里可以使用RequestBody解析。提交成功后，将响应码的状态码设置为201
    @PostMapping
    fun save(@RequestBody subscription: Subscription, response: HttpServletResponse): Any {
        // Use the application default credentials
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
        var docRef = db.collection("subscription").document(subscription.userId)
        val data = HashMap<String, Any>()
        data["userId"] = subscription.userId
        data["platform"] = subscription.platform
        data["city"] = subscription.city
        data["time"] = subscription.time
        val result = docRef.set(data)

        subscriptions.add(subscription)
        response.status = 201
        return subscription
    }

    //put修改。修改成功后，将响应码的状态码设置为201
    @PutMapping("/{index}")
    fun modify(@RequestBody subscription: Subscription, @PathVariable index: Int, response: HttpServletResponse): Any {
        //判断，返回错误码
        if (index >= subscriptions.count()) {
            response.status = 404
            return Result(ResultStatus.NOT_FOUND)
        }
        subscriptions[index] = subscription
        response.status = 201
        return subscriptions[index]
    }

    //delete删除。删除成功后，将响应码的状态设置为204
    @DeleteMapping
    fun delete(@RequestBody subscription: Subscription, response: HttpServletResponse): Any {
        // Use the application default credentials
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
        db.collection("subscription").document(subscription.userId)
                .delete()
        response.status = 204
        return Result(ResultStatus.DELETE_SUCCESS)
    }

    companion object {
        //数据源
        private var subscriptions = MutableList(4, { index -> Subscription("Person$index", 1, "台北市", "08:00") })
    }
}