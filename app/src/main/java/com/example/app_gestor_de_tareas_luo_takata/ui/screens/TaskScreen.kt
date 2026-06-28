package com.example.app_gestor_de_tareas_luo_takata.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.app_gestor_de_tareas_luo_takata.data.local.entities.Task
import com.example.app_gestor_de_tareas_luo_takata.ui.viewmodel.TaskViewModel

// Pantalla principal que muestra la lista de tareas y permite agregar nuevas
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(viewModel: TaskViewModel) {
    val tasks by viewModel.allTasks.collectAsState() // Observa la lista de tareas
    var showDialog by remember { mutableStateOf(false) } // Estado para mostrar el diálogo de creación
    var showEditDialog by remember { mutableStateOf(false) } // Estado para mostrar el diálogo de edición
    var newTaskTitle by remember { mutableStateOf("") } // Título de la nueva tarea
    var taskToEdit by remember { mutableStateOf<Task?>(null) } // Tarea seleccionada para editar
    var editTaskTitle by remember { mutableStateOf("") } // Título de la tarea a editar

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Gestor de Tareas") }) // Barra superior
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) { // Botón flotante para añadir
                Icon(Icons.Default.Add, contentDescription = "Agregar Tarea")
            }
        }
    ) { padding ->
        // Lista de tareas usando LazyColumn para eficiencia
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(tasks) { task ->
                TaskItem(
                    task = task,
                    onToggle = { viewModel.toggleTaskCompletion(task) },
                    onDelete = { viewModel.deleteTask(task) },
                    onEdit = {
                        taskToEdit = task
                        editTaskTitle = task.title
                        showEditDialog = true
                    }
                )
            }
        }

        // Diálogo para ingresar el título de una nueva tarea
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Nueva Tarea") },
                text = {
                    TextField(
                        value = newTaskTitle,
                        onValueChange = { newTaskTitle = it },
                        label = { Text("Título de la tarea") }
                    )
                },
                confirmButton = {
                    Button(onClick = {
                        viewModel.addTask(newTaskTitle)
                        newTaskTitle = ""
                        showDialog = false
                    }) {
                        Text("Agregar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("Cancelar")
                    }
                }
            )
        }

        // Diálogo para editar el título de una tarea existente
        if (showEditDialog && taskToEdit != null) {
            AlertDialog(
                onDismissRequest = { showEditDialog = false },
                title = { Text("Editar Tarea") },
                text = {
                    TextField(
                        value = editTaskTitle,
                        onValueChange = { editTaskTitle = it },
                        label = { Text("Nuevo título") }
                    )
                },
                confirmButton = {
                    Button(onClick = {
                        taskToEdit?.let {
                            viewModel.updateTask(it, editTaskTitle)
                        }
                        showEditDialog = false
                        taskToEdit = null
                    }) {
                        Text("Guardar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { 
                        showEditDialog = false
                        taskToEdit = null
                    }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}

// Componente para representar una fila de tarea individual
@Composable
fun TaskItem(task: Task, onToggle: () -> Unit, onDelete: () -> Unit, onEdit: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = task.completed,
                    onCheckedChange = { onToggle() } // Acción al marcar/desmarcar
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = task.title,
                    textDecoration = if (task.completed) TextDecoration.LineThrough else TextDecoration.None // Tacha si está completada
                )
            }
            Row {
                IconButton(onClick = onEdit) { // Botón para editar la tarea
                    Icon(Icons.Default.Edit, contentDescription = "Editar", tint = MaterialTheme.colorScheme.primary)
                }
                IconButton(onClick = onDelete) { // Botón para eliminar la tarea
                    Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}
