package com.example.app_gestor_de_tareas_luo_takata

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.app_gestor_de_tareas_luo_takata.data.local.database.AppDatabase
import com.example.app_gestor_de_tareas_luo_takata.data.repository.TaskRepository
import com.example.app_gestor_de_tareas_luo_takata.ui.screens.TaskScreen
import com.example.app_gestor_de_tareas_luo_takata.ui.theme.App_Gestor_de_Tareas_Luo_TakataTheme
import com.example.app_gestor_de_tareas_luo_takata.ui.viewmodel.TaskViewModel
import com.example.app_gestor_de_tareas_luo_takata.ui.viewmodel.TaskViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Inicialización de la base de datos y repositorio
        val database = AppDatabase.getDatabase(this)
        val repository = TaskRepository(database.taskDao())
        
        enableEdgeToEdge()
        setContent {
            App_Gestor_de_Tareas_Luo_TakataTheme {
                // Obtención del ViewModel usando la Factory
                val taskViewModel: TaskViewModel = viewModel(
                    factory = TaskViewModelFactory(repository)
                )
                
                // Carga de la pantalla principal de tareas
                TaskScreen(viewModel = taskViewModel)
            }
        }
    }
}
