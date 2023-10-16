package com.example.todolist.todo.application

import com.example.todolist.todo.dao.TodoDao
import com.example.todolist.todo.domain.Todo
import com.example.todolist.todo.dto.ApiResponse
import com.example.todolist.todo.dto.TodoRequest
import com.example.todolist.todo.dto.TodoResponse
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.RestTemplate
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter


@Service
@Transactional(readOnly = false)
class TodoServiceImpl(val todoDao: TodoDao): TodoService {
    override fun upload(uploadTodoRequest: TodoRequest.UploadTodoRequest): TodoResponse.PublicTodoResponse {
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")

        val todo = todoDao.create(
            Todo.create(
                LocalDate.parse(uploadTodoRequest.date, dateFormatter),
                LocalTime.parse(uploadTodoRequest.startTime, timeFormatter),
                LocalTime.parse(uploadTodoRequest.endTime, timeFormatter),
                uploadTodoRequest.todoTitle,
                uploadTodoRequest.todoDescription,
                uploadTodoRequest.completeYN
            )
        )
        return TodoResponse.PublicTodoResponse(
            todo.todoSeq!!,
            todo.date,
            todo.startTime,
            todo.endTime,
            todo.todoTitle,
            todo.todoDescription,
            todo.completeYN
        )
    }

    override fun modify(modifyTodoRequest: TodoRequest.ModifyTodoRequest): TodoResponse.PublicTodoResponse {
        val todo = todoDao.getTodoBySeq(modifyTodoRequest.todoSeq) ?: throw IllegalStateException("존재하지 않는 todo")
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")
        todo.apply {
            modifyTodoRequest.date?.let {
                updateDate(LocalDate.parse(it, dateFormatter))
            }
            modifyTodoRequest.startTime?.let {
                updateStartTime(LocalTime.parse(it, timeFormatter))
            }
            modifyTodoRequest.endTime?.let {
                updateEndTime(LocalTime.parse(it, timeFormatter))
            }
            modifyTodoRequest.todoTitle?.let {
                updateTodoTitle(it)
            }
            modifyTodoRequest.todoDescription?.let {
                updateTodoDescription(it)
            }
            modifyTodoRequest.completeYN?.let {
                updateCompleteYN(it)
            }
        }

        return TodoResponse.PublicTodoResponse(
            todo.todoSeq!!,
            todo.date,
            todo.startTime,
            todo.endTime,
            todo.todoTitle,
            todo.todoDescription,
            todo.completeYN
        )
    }

    override fun modifyCompleteYN(completeRequest: TodoRequest.PublicTodoRequest): TodoResponse.PublicTodoResponse {
        val todo = todoDao.getTodoBySeq(completeRequest.todoSeq) ?: throw IllegalStateException("존재하지 않는 todo")
        todo.apply {
            if (0 == todo.completeYN) {
                updateCompleteYN(1)
            } else if (1 == todo.completeYN) {
                updateCompleteYN(0)
            } else {
                throw IllegalStateException("부적절한 complete 값")
            }
        }

        return TodoResponse.PublicTodoResponse(
            todo.todoSeq!!,
            todo.date,
            todo.startTime,
            todo.endTime,
            todo.todoTitle,
            todo.todoDescription,
            todo.completeYN
        )
    }

    override fun remove(deleteRequest: TodoRequest.PublicTodoRequest) {
        val todo = todoDao.getTodoBySeq(deleteRequest.todoSeq) ?: throw IllegalStateException("존재하지 않는 todo")
        todoDao.deleteBySeq(todo.todoSeq!!)
    }

    override fun getTodoListByDate(date: String): List<TodoResponse.PublicTodoResponse> {
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val todoList = todoDao.getTodoListByDate(LocalDate.parse(date, dateFormatter)) ?: throw IllegalStateException("해당 일자의 todo가 없음")
        val result: MutableList<TodoResponse.PublicTodoResponse> = mutableListOf()
        todoList.forEach {
            result.add(TodoResponse.PublicTodoResponse(
                it.todoSeq!!,
                it.date,
                it.startTime,
                it.endTime,
                it.todoTitle,
                it.todoDescription,
                it.completeYN
            ))
        }
        return result
    }

    override fun callApi(recommendTodoRequest: TodoRequest.RecommendTodoRequest): ResponseEntity<ApiResponse> {
        val url = "http://www.boredapi.com/api/activity/"
        val restTemplate = RestTemplate()

        return restTemplate.getForEntity(url, ApiResponse::class.java)
    }

}