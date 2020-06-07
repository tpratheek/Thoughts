package com.pratheek.thoughts

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "thought_table")
data class ThoughtEntity(
    @PrimaryKey
    @ColumnInfo(name = "thought_content")
    val thought_content: String,
    @ColumnInfo(name = "thought_text_style")
    val thoughtTextStyle: Int,
    @ColumnInfo(name = "thought_card_color")
    val thoughtCardColor: Int,
    @ColumnInfo(name = "thought_text_color")
    val thoughtTextColor: String
)