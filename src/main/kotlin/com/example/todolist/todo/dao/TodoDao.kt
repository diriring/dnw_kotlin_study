package com.example.todolist.todo.dao

import com.example.todolist.todo.domain.Todo
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class TodoDao(val todoRepository: TodoRepository) {
    fun create(todo: Todo): Todo = todoRepository.save(todo)
    fun getTodoBySeq(todoSeq: Long): Todo? = todoRepository.findByIdOrNull(todoSeq)
    fun deleteBySeq(todoSeq: Long) = todoRepository.deleteById(todoSeq)
    fun getTodoListByDate(date: LocalDate): List<Todo>? = todoRepository.findByDateOrderByStartTime(date)

}