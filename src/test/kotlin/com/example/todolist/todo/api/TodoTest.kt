package com.example.todolist.todo.api

import com.example.todolist.todo.domain.Todo
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class TodoTest: DescribeSpec({
    describe("todo 업로드") {
        context("모든 항목이 존재할때") {
            it("todo 생성한다") {
                val todo = Todo.create(
                    LocalDate.parse("2023-10-16", DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    LocalTime.parse("16:00:00", DateTimeFormatter.ofPattern("HH:mm:ss")),
                    LocalTime.parse("16:30:00", DateTimeFormatter.ofPattern("HH:mm:ss")),
                    "test title",
                    "test Description",
                    0
                )

                todo.date shouldBe  LocalDate.parse("2023-10-16", DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                todo.startTime shouldBe LocalTime.parse("16:00:00", DateTimeFormatter.ofPattern("HH:mm:ss"))
                todo.endTime shouldBe LocalTime.parse("16:30:00", DateTimeFormatter.ofPattern("HH:mm:ss"))
                todo.todoTitle shouldBe "test title"
                todo.todoDescription shouldBe "test Description"
                todo.completeYN shouldBe 0
            }
        }
    }
})