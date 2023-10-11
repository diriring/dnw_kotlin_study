package com.example.todolist.todo.application

import com.example.todolist.todo.dto.TodoRequest
import com.example.todolist.todo.dto.TodoResponse


interface TodoService {
    fun upload(uploadTodoRequest: TodoRequest.UploadTodoRequest): TodoResponse.PublicTodoResponse
    fun modify(modifyTodoRequest: TodoRequest.ModifyTodoRequest): TodoResponse.PublicTodoResponse
    fun modifyCompleteYN(completeRequest: TodoRequest.PublicTodoRequest): TodoResponse.PublicTodoResponse
    fun remove(deleteRequest: TodoRequest.PublicTodoRequest)
    fun getTodoListByDate(date: String): List<TodoResponse.PublicTodoResponse>
    fun recommendTodo(recommendTodoRequest: TodoRequest.RecommendTodoRequest)
}