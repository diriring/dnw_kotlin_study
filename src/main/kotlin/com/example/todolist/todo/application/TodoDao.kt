package com.example.todolist.todo.application

import com.example.todolist.todo.domain.Todo
import java.time.LocalDate

interface TodoDao {
    fun create(todo: Todo): Todo
    fun getTodoBySeq(todoSeq: Long): Todo?
    fun deleteBySeq(todoSeq: Long)
    fun getTodoListByDate(date: LocalDate): List<Todo>?
}