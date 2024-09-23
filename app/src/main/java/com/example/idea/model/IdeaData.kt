package com.example.idea.model

import kotlinx.serialization.Serializable

@Serializable
data class IdeaData(
    val id: Int,
    var name: String,
    var author: String,
    var description: String,
    var status: Int
)