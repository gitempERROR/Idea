package com.example.idea.model

import kotlinx.serialization.Serializable

@Serializable
data class IdeaDataForInsert(
    var name: String,
    var author: String,
    var description: String,
    var status: Int
)