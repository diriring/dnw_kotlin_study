package com.example.todolist.todo.dto

sealed class TodoRequest (

    open val todoSeq: Long

) {
    data class UploadTodoRequest (
        val date: String,
        val startTime: String,
        val endTime: String,
        val todoTitle: String,
        val todoDescription: String,
        val completeYN: Int = 0
    )

    data class ModifyTodoRequest (
        override val todoSeq: Long,
        val date: String?,
        val startTime: String?,
        val endTime: String?,
        val todoTitle: String?,
        val todoDescription: String?,
        val completeYN: Int?
    ): TodoRequest(todoSeq)

    data class RecommendTodoRequest (
        val date: String,
        val startTime: String,
        val endTime: String
    )

    data class PublicTodoRequest (
        override val todoSeq: Long
    ): TodoRequest(todoSeq)
}