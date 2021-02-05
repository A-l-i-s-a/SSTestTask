package com.ivlieva.sstesttask.ui.item_task

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

@ExperimentalCoroutinesApi
class ItemTaskViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val taskRepository: TaskRepository
) : ViewModel() {
    private val _dataState: MutableLiveData<DataState<Task>> = MutableLiveData()

    val dataState: LiveData<DataState<Task>>
        get() = _dataState

    fun getCurrentTask(id: Long) {
        viewModelScope.launch {
            taskRepository.getTaskById(id)
                .onEach { dataState ->
                    _dataState.value = dataState
                }
                .launchIn(viewModelScope)
        }
    }

}