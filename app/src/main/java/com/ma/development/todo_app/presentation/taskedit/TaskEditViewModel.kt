package com.ma.development.todo_app.presentation.taskedit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ma.development.todo_app.domain.model.Task
import com.ma.development.todo_app.domain.usecases.DeleteTaskUseCase
import com.ma.development.todo_app.domain.usecases.GetTaskByIdUseCase
import com.ma.development.todo_app.domain.usecases.UpdateTaskStatusUseCase
import com.ma.development.todo_app.domain.usecases.UpdateTaskStatusUseCase_Factory
import com.ma.development.todo_app.domain.util.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskEditViewModel @Inject constructor(
    private val getTaskByIdUseCase: GetTaskByIdUseCase,
    private val updateTaskStatusUseCase: UpdateTaskStatusUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
) : ViewModel() {

    private val _savedSuccessfully by lazy { MutableLiveData<Boolean>() }
    val savedSuccessfully: LiveData<Boolean> by lazy { _savedSuccessfully }

    private val _deletedSuccessfully by lazy { MutableLiveData<Boolean>() }
    val deletedSuccessfully: LiveData<Boolean> by lazy { _deletedSuccessfully }

    private val _error: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val error: LiveData<String> by lazy { _error }

    fun getTask(taskId: String): LiveData<Task> = getTaskByIdUseCase.execute(taskId)

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            deleteTaskUseCase.execute(task)
            _deletedSuccessfully.value = true
        }
    }

    fun updateTaskStatus(isCompleted: Boolean, taskId: String) {
        viewModelScope.launch {
            updateTaskStatusUseCase.execute(isCompleted = isCompleted, taskId = taskId)
            _savedSuccessfully.value = true
        }
    }

}