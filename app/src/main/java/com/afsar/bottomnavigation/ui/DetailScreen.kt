package com.afsar.bottomnavigation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import com.afsar.bottomnavigation.data.ToDo
import com.afsar.bottomnavigation.ui.viewmodel.ToDoViewModel
import com.afsar.bottomnavigation.utility.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(onBackClicked: () -> Unit,
                 todo: ToDo?) {
    val context = LocalContext.current
    val toDoViewModel: ToDoViewModel = viewModel(
        factory = ViewModelFactory((context.applicationContext as ToDoApplication).repository)
    )

    val liveTodo by toDoViewModel.getTodoById(todo?.id ?: -1L)
        .collectAsState(initial = todo)

    var isInEditMode by remember { mutableStateOf(false) }

    var editedTitle by remember(liveTodo) { mutableStateOf(liveTodo?.title ?: "") }
    var editedDesc by remember(liveTodo) { mutableStateOf(liveTodo?.description ?: "") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail Tugas") },
                navigationIcon = {
                    IconButton(onClick = onBackClicked) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Kembali"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            if (liveTodo != null) {
                FloatingActionButton(
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
                    }
                ) {
                    Icon(
                        imageVector = if (isInEditMode) Icons.Default.Save else Icons.Default.Edit,
                        contentDescription = if (isInEditMode) "Simpan" else "Edit"
                    )
                }
            }
        }
    ) { innerPadding ->
        if (liveTodo != null) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding)
                    .padding(16.dp),
            ) {
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
                Text(text = "Status: ${if (liveTodo!!.isCompleted) "Selesai" else "Belum Selesai"}")
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Data ToDo tidak ditemukan.")
            }
        }
    }
}