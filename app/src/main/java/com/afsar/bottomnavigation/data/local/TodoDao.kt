package com.afsar.bottomnavigation.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.afsar.bottomnavigation.data.ToDo
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {


    @Query("SELECT * FROM todo_table ORDER BY id DESC")
    fun getAllTodos(): Flow<List<ToDo>>


    @Query("SELECT * FROM todo_table WHERE id = :id")
    fun getTodoById(id: Long): Flow<ToDo?>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(todo: ToDo)

    @Update
    suspend fun update(todo: ToDo)

    @Delete
    suspend fun delete(todo: ToDo)
}