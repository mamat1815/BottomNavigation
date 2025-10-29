package com.afsar.bottomnavigation.utility

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.afsar.bottomnavigation.data.local.ToDoRepository
import com.afsar.bottomnavigation.ui.viewmodel.ToDoViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val dataRepository: ToDoRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ToDoViewModel::class.java) -> ToDoViewModel(
                dataRepository
            ) as T


            else -> throw IllegalArgumentException("Unknown ViewModel: " + modelClass.name)
        }

    }
}