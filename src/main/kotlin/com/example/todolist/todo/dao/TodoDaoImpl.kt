package com.example.todolist.todo.dao

import com.example.todolist.todo.application.TodoDao
import com.example.todolist.todo.domain.Todo
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class TodoDaoImpl(val todoRepository: TodoRepository): TodoDao {
    override fun create(todo: Todo): Todo = todoRepository.save(todo)
    override fun getTodoBySeq(todoSeq: Long): Todo? = todoRepository.findByIdOrNull(todoSeq)
    override fun deleteBySeq(todoSeq: Long) = todoRepository.deleteById(todoSeq)
    override fun getTodoListByDate(date: LocalDate): List<Todo>? = todoRepository.findByDateOrderByStartTime(date)

}