package com.beerbucket

data class Ingredient(
    val name: String,
    var likeCount: Int = 0,
    var dislikeCount: Int = 0
) {
    val score: Int
        get() = likeCount - dislikeCount

    override fun toString() = name

}
