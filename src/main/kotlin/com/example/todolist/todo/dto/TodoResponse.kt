package com.example.todolist.todo.dto

import java.time.LocalDate
import java.time.LocalTime

sealed class TodoResponse (
    open val date: LocalDate,
    open val starTime: LocalTime,
    open val endTime: LocalTime,
    open val todoTitle: String,
    open val todoDescription: String
) {
    data class PublicTodoResponse(
        val todoSeq: Long,
        override val date: LocalDate,
        override val starTime: LocalTime,
        override val endTime: LocalTime,
        override val todoDescription: String,
        override val todoTitle: String,
        val completeYN: Int
    ): TodoResponse(date, starTime, endTime, todoTitle, todoDescription)
}