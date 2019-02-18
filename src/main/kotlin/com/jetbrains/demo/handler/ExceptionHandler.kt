package com.jetbrains.demo.handler

import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import com.jetbrains.demo.result.Result
import com.jetbrains.demo.result.ResultStatus
import javax.servlet.http.HttpServletResponse


/**
 * Created by AlexHe on 2019-02-15.
 * Describe
 */

@ControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(value = [Exception::class])
    @ResponseBody
    fun error(e: Exception, response: HttpServletResponse): Any {
        response.status = 500
        return e
    }
}