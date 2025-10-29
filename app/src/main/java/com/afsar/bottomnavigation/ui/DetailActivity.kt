package com.afsar.bottomnavigation.ui

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.afsar.bottomnavigation.data.ToDo
import com.afsar.bottomnavigation.ui.ui.theme.BottomNavigationTheme

class DetailActivity : ComponentActivity() {
    private val todoItem: ToDo? by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent?.getParcelableExtra(TODO_EXTRA_KEY, ToDo::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent?.getParcelableExtra(TODO_EXTRA_KEY)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BottomNavigationTheme {
                DetailScreen(
                    todo = todoItem,
                    onBackClicked = { finish() }
                )
            }
        }
    }


    companion object {
        private const val TODO_EXTRA_KEY = "todo_item"
        fun newIntent(context: Context, todo: ToDo): Intent {
            return Intent(context, DetailActivity::class.java).apply {
                putExtra(TODO_EXTRA_KEY, todo)
            }
        }
    }
}