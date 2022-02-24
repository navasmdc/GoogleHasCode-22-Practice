package com.beerbucket

import java.io.File

fun main(args: Array<String>) {
    File("input").listFiles().sortedBy { it.name }.forEachIndexed { index, file ->
        val input = file.readText()
        val output = execute(input)
        printOutput(output, index)
    }
}

fun execute(input: String): String {
    var ingredients = mutableMapOf<String, Ingredient>()
    val clients = mutableListOf<Client>()
    val lines = input.splitPro("\n").also { it.removeAt(0) }
    for (i in 0 until lines.size step 2) {
        val client = Client("Client$i")
        lines[i].splitPro(" ").also { it.removeAt(0) }.forEach { ing ->
            val ingredient = ingredients[ing] ?: Ingredient(ing).also { ingredients[ing] = it }
            ingredient.likeCount++
            client.likes.add(
                ingredient
            )
        }
        lines[i + 1].splitPro(" ").also { it.removeAt(0) }.forEach { ing ->
            val ingredient = ingredients[ing] ?: Ingredient(ing).also { ingredients[ing] = it }
            ingredient.dislikeCount++
            client.dislikes.add(
                ingredient
            )
        }
        clients.add(client)
    }

    val pizza = mutableListOf<Ingredient>()
    val candidates = ingredients.filter { it.value.likeCount > 0 }.values.toMutableList()
    while (candidates.isNotEmpty()) {
        val ingredient = candidates.maxBy { it.dislikeCount }
        if (ingredient == null || ingredient.dislikeCount == 0) {
            candidates.forEach { pizza.add(it) }
            break
        } else if (ingredient.score > 0) {
            pizza.add(ingredient)
            val toRemove = mutableListOf<Client>()
            clients.forEach {
                if (it.dislikes.contains(ingredient)) {
                    it.likes.forEach { it.likeCount-- }
                    it.dislikes.forEach { it.dislikeCount-- }
                    toRemove.add(it)
                }
            }
            toRemove.forEach { clients.remove(it) }
        }
        candidates.remove(ingredient)
    }

    return "${pizza.size} ${pizza.joinToString(separator = " ")}"
}

fun printOutput(output: String, index: Int) = File("output/outputFile$index").writeText(output)

fun String.splitPro(string: String) = split(string).toMutableList().also { it.removeIf { s -> s.isEmpty() } }



