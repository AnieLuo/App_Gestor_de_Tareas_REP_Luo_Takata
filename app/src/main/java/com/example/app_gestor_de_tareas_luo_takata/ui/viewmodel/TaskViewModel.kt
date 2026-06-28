package com.example.app_gestor_de_tareas_luo_takata.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.app_gestor_de_tareas_luo_takata.data.local.entities.Task
import com.example.app_gestor_de_tareas_luo_takata.data.repository.TaskRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

// ViewModel para manejar la lógica de la UI y comunicación con el repositorio
class TaskViewModel(private val repository: TaskRepository) : ViewModel() {

    // Lista de tareas observada como StateFlow para la UI
    val allTasks: StateFlow<List<Task>> = repository.allTasks
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Función para insertar una nueva tarea
    fun addTask(title: String) {
        viewModelScope.launch {
            if (title.isNotBlank()) {
                repository.insert(Task(title = title))
            }
        }
    }

    // Función para actualizar el estado de completado de una tarea
    fun toggleTaskCompletion(task: Task) {
        viewModelScope.launch {
            repository.update(task.copy(completed = !task.completed))
        }
    }

    // Función para editar el título de una tarea
    fun updateTask(task: Task, newTitle: String) {
        viewModelScope.launch {
            if (newTitle.isNotBlank()) {
                repository.update(task.copy(title = newTitle))
            }
        }
    }

    // Función para eliminar una tarea
    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.delete(task)
        }
    }
}

// Factory para instanciar el ViewModel con el repositorio
class TaskViewModelFactory(private val repository: TaskRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TaskViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
