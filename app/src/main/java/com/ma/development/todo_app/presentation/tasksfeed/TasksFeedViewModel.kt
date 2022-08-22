package com.ma.development.todo_app.presentation.tasksfeed

import androidx.lifecycle.*
import com.ma.development.todo_app.domain.model.Task
import com.ma.development.todo_app.domain.usecases.*
import com.ma.development.todo_app.domain.util.onFailure
import com.ma.development.todo_app.domain.util.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksFeedViewModel @Inject constructor(
    getAllTasksUseCase: GetAllTasksUseCase,
    getCompletedTasksUseCase: GetCompletedTasksUseCase,
    getInCompleteTasksUseCase: GetInCompleteTasksUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val addTaskUseCase: AddTaskUseCase,
    private val refreshTasksUseCase: RefreshTasksUseCase
) : ViewModel() {

    private val _addingOrDeletingLoading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(
            false
        )
    }
    val addingOrDeletingLoading: LiveData<Boolean> by lazy { _addingOrDeletingLoading }

    private val _error: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val error: LiveData<String> by lazy { _error }

    private val filterType = MutableLiveData<FilterType>(FilterType.ALL)


    val tasks: LiveData<List<Task>> = Transformations.switchMap(filterType) {
        return@switchMap when (it) {
            FilterType.COMPLETED -> getCompletedTasksUseCase.execute()
            FilterType.INCOMPLETE -> getInCompleteTasksUseCase.execute()
            else -> getAllTasksUseCase.execute()
        }
    }

    private val _seeAllVisibility by lazy { MutableLiveData<Boolean>() }
    val seeAllVisibility: LiveData<Boolean> by lazy { _seeAllVisibility }

    var firstTime = true
    private val limit = 5
    var skip = 0
    private var tasksSize = 0


    private val _initialLoadingVisibility = MutableLiveData<Boolean>(true)
    val initialLoadingVisibility: LiveData<Boolean> = _initialLoadingVisibility

    fun getTasks() {
        viewModelScope.launch {
            refreshTasksUseCase.execute(limit, skip).onSuccess {
                skip += 5
                tasksSize = it
                checkMoreDataExistence()
            }.onFailure {
                _error.value = it.message.toString()
            }
            handleInitialVisibilities()
        }
    }

    fun filter(type: FilterType) {
        filterType.value = type
    }

    fun deleteTask(task: Task) {
        _addingOrDeletingLoading.value = true
        viewModelScope.launch {
            deleteTaskUseCase.execute(task)
            _addingOrDeletingLoading.value = false
        }
    }

    fun addTask(description: String) {
        _addingOrDeletingLoading.value = true
        viewModelScope.launch {
            addTaskUseCase.execute(description)
                .onFailure {
                    _error.value = it.message.toString()
                }
            _addingOrDeletingLoading.value = false
        }
    }

    private fun checkMoreDataExistence() {
        if (tasksSize < limit || tasksSize == 0)
            _seeAllVisibility.value = false
    }

    private fun handleInitialVisibilities() {
        if (firstTime) {
            _initialLoadingVisibility.value = false
            if (tasksSize > 0)
                _seeAllVisibility.value = true
            firstTime = false
        }
    }

}