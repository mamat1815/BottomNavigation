package com.afsar.bottomnavigation.data.local

import com.afsar.bottomnavigation.data.ToDo
import kotlinx.coroutines.flow.Flow


class ToDoRepository(private val todoDao: ToDoDao) {


    val allTodos: Flow<List<ToDo>> = todoDao.getAllTodos()


    fun getTodoById(id: Long): Flow<ToDo?> {
        return todoDao.getTodoById(id)
    }

    suspend fun insert(todo: ToDo) {
        todoDao.insert(todo)
    }

    suspend fun update(todo: ToDo) {
        todoDao.update(todo)
    }

    suspend fun delete(todo: ToDo) {
        todoDao.delete(todo)
    }
}