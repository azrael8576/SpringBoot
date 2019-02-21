package com.jetbrains.demo.controller

import com.jetbrains.demo.bean.Person
import org.springframework.web.bind.annotation.*
import com.jetbrains.demo.result.Result
import com.jetbrains.demo.result.ResultStatus
import javax.servlet.http.HttpServletResponse
import javax.validation.Valid


/**
 * Created by AlexHe on 2019-02-15.
 * Describe
 */

@RestController
@RequestMapping("/persons")
class PersonController {

    //可以直接返回
    @GetMapping
    fun getAll(): Any = persons

    //直接返回可省略返回类型，因为可以自动推断返回值；另外注意路径变量的写法
    @GetMapping("/{index}")
    fun get(@PathVariable index: Int) = persons[index]

    //前端直接提交JSON对象，这里可以使用RequestBody解析。提交成功后，将响应码的状态码设置为201
    @PostMapping
    fun save(@RequestBody person: Person, response: HttpServletResponse): Any {
        persons.add(person)
        response.status = 201
        return person
    }

    //put修改。修改成功后，将响应码的状态码设置为201
    @PutMapping("/{index}")
    fun modify(@RequestBody person: Person, @PathVariable index: Int, response: HttpServletResponse): Any {
        //判断，返回错误码
        if (index >= persons.count()) {
            response.status = 404
            return Result(ResultStatus.NOT_FOUND)
        }
        persons[index] = person
        response.status = 201
        return persons[index]
    }

    //delete删除。删除成功后，将响应码的状态设置为204
    @DeleteMapping("/{index}")
    fun delete(@PathVariable index: Int, response: HttpServletResponse): Any {
        persons.removeAt(index)
        response.status = 204
        return Result(ResultStatus.DELETE_SUCCESS)
    }

    companion object {
        //数据源
        private var persons = MutableList(4, { index -> Person("Person$index", 20 + index, index % 2 == 0) })
    }
}