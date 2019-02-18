package com.jetbrains.demo.result


/**
 * Created by AlexHe on 2019-02-15.
 * Describe
 */

enum class ResultStatus(val code: Int, val message: String) {
    DELETE_SUCCESS(204, "刪除成功"),
    NOT_FOUND(404, "找不到對應資料"),
    UNKNOWN_ERROR(500, "未知錯誤"),
}