package com.example.todolist.todo.domain

import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalTime

@Entity
@Table(name = "tTodo")
class Todo(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todoSeq")
    val todoSeq: Long?,

    @Column(name = "date")
    var date: LocalDate,

    @Column(name = "startTime")
    var startTime: LocalTime,

    @Column(name = "endTime")
    var endTime: LocalTime,

    @Column(name = "todoTitle")
    var todoTitle: String,

    @Column(name = "todoDescription")
    var todoDescription: String,

    @Column(name = "completeYN")
    var completeYN: Int = 0

) {
    companion object {
        fun create(date: LocalDate, startTime:LocalTime, endTime: LocalTime, todoTitle: String, todoDescription: String, completeYN: Int): Todo {
            return Todo(null, date, startTime, endTime, todoTitle, todoDescription, completeYN)
        }
    }

    fun updateDate(date: LocalDate) {
        this.date = date
    }
    fun updateStartTime(startTime: LocalTime) {
        this.startTime = startTime
    }

    fun updateEndTime(endTime: LocalTime) {
        this.endTime = endTime
    }

    fun updateTodoTitle(todoTitle: String) {
        this.todoTitle = todoTitle
    }

    fun updateTodoDescription(todoDescription: String) {
        this.todoDescription = todoDescription
    }

    fun updateCompleteYN(completeYN: Int) {
        this.completeYN = completeYN
    }

}
