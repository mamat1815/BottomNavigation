package com.afsar.bottomnavigation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.afsar.bottomnavigation.data.ToDo
import com.afsar.bottomnavigation.ui.viewmodel.ToDoViewModel
import com.afsar.bottomnavigation.utility.ViewModelFactory

@Composable
fun DetailScreen(navController: NavController, todo: ToDo?) {
    val context = LocalContext.current
    val toDoViewModel: ToDoViewModel = viewModel(
        factory = ViewModelFactory((context.applicationContext as ToDoApplication).repository)
    )

    val liveTodo by toDoViewModel.getTodoById(todo?.id ?: -1L)
        .collectAsState(initial = todo)

    var isInEditMode by remember { mutableStateOf(false) }

    var editedTitle by remember(liveTodo) { mutableStateOf(liveTodo?.title ?: "") }
    var editedDesc by remember(liveTodo) { mutableStateOf(liveTodo?.description ?: "") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = { navController.navigateUp() },
                modifier = Modifier.height(36.dp)
            ) {
                Text("← Back")
            }

            if (liveTodo != null) {
                Button(
                    onClick = {
                        if (isInEditMode) {
                            val updatedTodo = liveTodo!!.copy(
                                title = editedTitle,
                                description = editedDesc
                            )
                            toDoViewModel.updateTodo(updatedTodo)
                            isInEditMode = false
                        } else {
                            isInEditMode = true
                        }
                    },
                    modifier = Modifier.height(36.dp)
                ) {
                    Text(if (isInEditMode) "Simpan" else "Edit")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (liveTodo != null) {
            if (isInEditMode) {
                OutlinedTextField(
                    value = editedTitle,
                    onValueChange = { editedTitle = it },
                    label = { Text("Judul Tugas") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = editedDesc,
                    onValueChange = { editedDesc = it },
                    label = { Text("Deskripsi") },
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                Text(
                    text = liveTodo!!.title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = liveTodo!!.description)
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Status: ${if (liveTodo!!.isCompleted) "Selesai" else "Belum Selesai"}") // Baca dari liveTodo
        } else {
            Text(text = "Data ToDo tidak ditemukan.")
        }
    }
}