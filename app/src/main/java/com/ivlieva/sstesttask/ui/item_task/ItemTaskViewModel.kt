package com.ivlieva.sstesttask.ui.item_task

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.ivlieva.sstesttask.repository.TaskRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class ItemTaskViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val taskRepository: TaskRepository
) : ViewModel() {
    // TODO: Implement the ViewModel
}