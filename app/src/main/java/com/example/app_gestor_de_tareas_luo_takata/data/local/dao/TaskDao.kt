package com.example.app_gestor_de_tareas_luo_takata.data.local.dao

import androidx.room.*
import com.example.app_gestor_de_tareas_luo_takata.data.local.entities.Task
import kotlinx.coroutines.flow.Flow

// Interface que define las operaciones de la base de datos para las tareas
@Dao
interface TaskDao {
    // Obtiene todas las tareas ordenadas por ID descendente
    @Query("SELECT * FROM tasks ORDER BY id DESC")
    fun getAllTasks(): Flow<List<Task>>

    // Inserta una nueva tarea en la base de datos
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)

    // Actualiza una tarea existente
    @Update
    suspend fun updateTask(task: Task)

    // Elimina una tarea de la base de datos
    @Delete
    suspend fun deleteTask(task: Task)
}
