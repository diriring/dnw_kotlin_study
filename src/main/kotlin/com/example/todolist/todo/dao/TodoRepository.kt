package com.example.todolist.todo.dao

import com.example.todolist.todo.domain.Todo
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface TodoRepository: JpaRepository<Todo,Long> {
    fun findByDateOrderByStartTime(date: LocalDate): List<Todo>?
}