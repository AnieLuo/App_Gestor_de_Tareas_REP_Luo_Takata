package com.example.app_gestor_de_tareas_luo_takata.data.repository

import com.example.app_gestor_de_tareas_luo_takata.data.local.dao.TaskDao
import com.example.app_gestor_de_tareas_luo_takata.data.local.entities.Task
import kotlinx.coroutines.flow.Flow

// Repositorio para gestionar el acceso a los datos de las tareas
class TaskRepository(private val taskDao: TaskDao) {
    // Flujo con todas las tareas
    val allTasks: Flow<List<Task>> = taskDao.getAllTasks()

    // Inserta una tarea usando el DAO
    suspend fun insert(task: Task) {
        taskDao.insertTask(task)
    }

    // Actualiza una tarea usando el DAO
    suspend fun update(task: Task) {
        taskDao.updateTask(task)
    }

    // Elimina una tarea usando el DAO
    suspend fun delete(task: Task) {
        taskDao.deleteTask(task)
    }
}
