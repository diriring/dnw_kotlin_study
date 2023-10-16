package com.example.todolist.todo.api

import com.example.todolist.global.common.response.CommonResponse
import com.example.todolist.todo.application.TodoService
import com.example.todolist.todo.dto.TodoRequest
import com.example.todolist.todo.dto.TodoResponse
import org.slf4j.LoggerFactory
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/todo")
class TodoController(val todoService: TodoService) {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    @GetMapping("/dummy")
    fun dummy():String {
        return "Hello World! - TodoList"
    }

    @GetMapping("/{date}")
    fun getTodoList(@PathVariable date: String): CommonResponse<List<TodoResponse.PublicTodoResponse>> {
        logger.info("date : {}", date)

        val todoResponseList = todoService.getTodoListByDate(date)
        return CommonResponse.success(todoResponseList, "일자로 todoList 조회 성공")
    }

    @PostMapping
    fun upload(@RequestBody @Validated uploadTodoRequest: TodoRequest.UploadTodoRequest): CommonResponse<TodoResponse> {
        logger.info("date : {}", uploadTodoRequest.date)
        logger.info("startTime : {}", uploadTodoRequest.startTime)
        logger.info("endTime : {}", uploadTodoRequest.endTime)
        logger.info("todoTitle : {}", uploadTodoRequest.todoTitle)
        logger.info("todoDescription : {}", uploadTodoRequest.todoDescription)
        logger.info("completeYN : {}", uploadTodoRequest.completeYN)

        val todoResponse = todoService.upload(uploadTodoRequest)

        return CommonResponse.success(todoResponse, "todo 등록 성공")
    }

    @PutMapping
    fun modify(@RequestBody @Validated modifyTodoRequest: TodoRequest.ModifyTodoRequest): CommonResponse<TodoResponse> {
        logger.info("todoSeq : {}", modifyTodoRequest.todoSeq)
        logger.info("date : {}", modifyTodoRequest.date)
        logger.info("startTime : {}", modifyTodoRequest.startTime)
        logger.info("endTime : {}", modifyTodoRequest.endTime)
        logger.info("todoTitle : {}", modifyTodoRequest.todoTitle)
        logger.info("todoDescription : {}", modifyTodoRequest.todoDescription)
        logger.info("completeYN : {}", modifyTodoRequest.completeYN)

        val todoResponse = todoService.modify(modifyTodoRequest)
        
        return CommonResponse.success(todoResponse, "todo 수정 성공")
    }

    @PutMapping("/complete")
    fun modifyCompleteYN(@RequestBody @Validated completeRequest: TodoRequest.PublicTodoRequest): CommonResponse<TodoResponse> {
        logger.info("todoSeq : {}", completeRequest.todoSeq)

        val todoResponse = todoService.modifyCompleteYN(completeRequest)

        return CommonResponse.success(todoResponse, "complete 수정 성공")
    }

    @DeleteMapping
    fun remove(@RequestBody @Validated deleteRequest: TodoRequest.PublicTodoRequest) {
        logger.info("todoSeq : {}", deleteRequest.todoSeq)
        todoService.remove(deleteRequest)
    }

    @PostMapping("/recommend")
    fun recommendTodo(@RequestBody @Validated recommendTodoRequest: TodoRequest.RecommendTodoRequest): CommonResponse<TodoResponse.PublicTodoResponse> {
        logger.info("date : {}", recommendTodoRequest.date)
        logger.info("startTime : {}", recommendTodoRequest.startTime)
        logger.info("endTime : {}", recommendTodoRequest.endTime)

        val response = todoService.callApi(recommendTodoRequest)

        logger.info("statusCode : {}", response.statusCode)
        logger.info("body : {}", response.body)

        val uploadTodoRequest = response.body?.let {
            TodoRequest.UploadTodoRequest(
                recommendTodoRequest.date,
                recommendTodoRequest.startTime,
                recommendTodoRequest.endTime,
                "추천 할일",
                it.activity,
                0
            )
        }

        checkNotNull(uploadTodoRequest) {
            "api 호출 실패"
        }
        val todoResponse = todoService.upload(uploadTodoRequest!!)
        return CommonResponse.success(todoResponse, "todo 등록 성공")
    }

}