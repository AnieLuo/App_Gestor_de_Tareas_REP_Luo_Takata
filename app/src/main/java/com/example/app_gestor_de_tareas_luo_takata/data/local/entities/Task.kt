package com.example.app_gestor_de_tareas_luo_takata.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

// Entidad que representa la tabla "tasks" en la base de datos
@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0, // Identificador único autogenerado
    val title: String, // Título o descripción de la tarea
    val completed: Boolean = false // Estado de la tarea (completada o pendiente)
)
