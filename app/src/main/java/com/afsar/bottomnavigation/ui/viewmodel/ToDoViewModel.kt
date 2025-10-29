package com.afsar.bottomnavigation.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afsar.bottomnavigation.data.ToDo
import com.afsar.bottomnavigation.data.local.ToDoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ToDoViewModel(private val repository: ToDoRepository) : ViewModel() {


    val allTodos: StateFlow<List<ToDo>> = repository.allTodos.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )

    fun addTodo(title: String, description: String) {
        viewModelScope.launch {
            val newTodo = ToDo(title = title, description = description)
            repository.insert(newTodo)
        }
    }

    fun updateTodo(todo: ToDo) {
        viewModelScope.launch {
            repository.update(todo)
        }
    }

    fun deleteTodo(todo: ToDo) {
        viewModelScope.launch {
            repository.delete(todo)
        }
    }

    fun getTodoById(id: Long): Flow<ToDo?> {
        return repository.getTodoById(id)
    }
}