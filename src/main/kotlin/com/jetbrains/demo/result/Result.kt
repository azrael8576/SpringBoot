package com.jetbrains.demo.result


/**
 * Created by AlexHe on 2019-02-15.
 * Describe
 */

data class Result(val code: Int, val message: String) {
    constructor(status: ResultStatus) : this(status.code, status.message)
}