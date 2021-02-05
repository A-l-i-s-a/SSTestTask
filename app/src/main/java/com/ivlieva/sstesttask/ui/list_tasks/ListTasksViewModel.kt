package com.ivlieva.sstesttask.ui.list_tasks

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.ivlieva.sstesttask.entyty.Task
import com.ivlieva.sstesttask.repository.TaskRepository
import com.ivlieva.sstesttask.util.DataState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.*

@ExperimentalCoroutinesApi
class ListTasksViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val taskRepository: TaskRepository
) : ViewModel() {
    private val _dataState: MutableLiveData<DataState<List<Task>>> = MutableLiveData()

    val dataState: LiveData<DataState<List<Task>>>
        get() = _dataState

    fun setStateEvent(listTasksStateEvent: ListTasksStateEvent) {
        viewModelScope.launch {
            when (listTasksStateEvent) {
                is ListTasksStateEvent.GetTaskEvents -> {
                    taskRepository.getTasks()
                        .onEach { dataState ->
                            _dataState.value = dataState
                        }
                        .launchIn(viewModelScope)
                }
                is ListTasksStateEvent.GetTaskByDateEvents -> {
                    taskRepository.getTasksByDate(ListTasksStateEvent.GetTaskByDateEvents.date)
                        .onEach { dataState ->
                            _dataState.value = dataState
                        }
                        .launchIn(viewModelScope)
                }
                is ListTasksStateEvent.None -> {
                    //
                }

            }
        }
    }
}

sealed class ListTasksStateEvent {
    object GetTaskEvents : ListTasksStateEvent()
    object GetTaskByDateEvents : ListTasksStateEvent() {
        var date = Date().time
    }

    object None : ListTasksStateEvent()
}