package com.afsar.bottomnavigation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.afsar.bottomnavigation.data.ToDo
import com.afsar.bottomnavigation.ui.viewmodel.ToDoViewModel
import com.afsar.bottomnavigation.utility.ViewModelFactory

@Composable
fun HomeScreen() {
    val context = LocalContext.current
    val toDoViewModel: ToDoViewModel = viewModel(
        factory = ViewModelFactory((context.applicationContext as ToDoApplication).repository)
    )

    val todoList by toDoViewModel.allTodos.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(todoList) { todo ->
            ToDoItemCard(
                todo = todo,
                onClick = {
                    val intent = DetailActivity.newIntent(context, todo)
                    context.startActivity(intent)
                          },
                onDelete = { toDoViewModel.deleteTodo(todo) },
                onToggleComplete = {
                    toDoViewModel.updateTodo(todo.copy(isCompleted = !todo.isCompleted))
                }
            )
        }
    }
}

@Composable
fun ToDoItemCard(
    todo: ToDo,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    onToggleComplete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(todo.title, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(4.dp))
            Text(todo.description)
            Spacer(Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = onToggleComplete) {
                    Text(if (todo.isCompleted) "Tandai Belum" else "Tandai Selesai")
                }
                Button(onClick = onDelete, colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error)) {
                    Text("Hapus")
                }
            }
        }
    }
}
