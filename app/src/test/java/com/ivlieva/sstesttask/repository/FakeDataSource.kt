package com.ivlieva.sstesttask.repository

import com.ivlieva.sstesttask.entyty.Task

class FakeDataSource(var mutableList: MutableList<Task> = mutableListOf()): TaskDataSource<Task> {

    override suspend fun saveTask(task: Task) {
        mutableList.add(task)
    }

    override fun getTaskById(id: Long): Task {
        return mutableList.first { it.id == id }
    }

    override fun getTasksByDate(date: Long): List<Task> {
        TODO("Not yet implemented")
    }

    override fun getTasks(): List<Task> {
        TODO("Not yet implemented")
    }

    override fun saveTasks(tasks: List<Task>) {
        TODO("Not yet implemented")
    }

    override fun deleteTask(task: Task) {
        TODO("Not yet implemented")
    }

    override fun deleteAllTasks() {
        TODO("Not yet implemented")
    }

}