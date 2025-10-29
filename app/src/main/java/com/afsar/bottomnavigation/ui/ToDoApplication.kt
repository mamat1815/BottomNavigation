package com.afsar.bottomnavigation.ui

import android.app.Application
import com.afsar.bottomnavigation.data.local.AppDatabase
import com.afsar.bottomnavigation.data.local.ToDoRepository

class ToDoApplication : Application() {
    val database by lazy { AppDatabase.Companion.getDatabase(this) }
    val repository by lazy { ToDoRepository(database.todoDao()) }
}