package com.afsar.bottomnavigation.ui

import com.afsar.bottomnavigation.data.ToDo
import com.google.gson.Gson

sealed class Screen(val route: String) {

    object Home : Screen("home")
    object List : Screen("list")

    object Detail : Screen("detail?todo={todoJson}") {
        fun createRoute(todo: ToDo): String {
            val todoJson = Gson().toJson(todo)
            val encodedJson = java.net.URLEncoder.encode(todoJson, "UTF-8")
            return "detail?todo=$encodedJson"
        }
    }
}

