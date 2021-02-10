package com.ivlieva.sstesttask.repository


interface TaskDataSource<T> {
    suspend fun saveTask(task: T)
    fun getTaskById(id: Long): T
    fun getTasksByDate(date: Long): List<T>
    fun getTasks(): List<T>
    fun saveTasks(tasks: List<T>)
    fun deleteTask(task: T)
    fun deleteAllTasks()
}