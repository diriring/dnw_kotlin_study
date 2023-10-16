package com.example.todolist.todo.dto

data class ApiResponse (
    val activity: String,
    val type: String,
    val participants: String,
    val price: String,
    val link: String,
    val key: String,
    val accessibility: String
)