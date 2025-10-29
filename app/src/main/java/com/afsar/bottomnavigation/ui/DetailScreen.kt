package com.afsar.bottomnavigation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.afsar.bottomnavigation.data.ToDo

@Composable
fun DetailScreen(navController: NavController, todo: ToDo?) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { navController.navigateUp() },
                modifier = Modifier.height(36.dp)
            ) {
                Text("← Back")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (todo != null) {
            Text(
                text = todo.title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = todo.description)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Status: ${if (todo.isCompleted) "Selesai" else "Belum Selesai"}")
        } else {
            Text(text = "Data ToDo tidak ditemukan.")
        }
    }
}
