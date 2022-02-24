package com.beerbucket

data class Client(
    val name: String,
    val likes: MutableList<Ingredient> = mutableListOf(),
    val dislikes: MutableList<Ingredient> = mutableListOf()
)
