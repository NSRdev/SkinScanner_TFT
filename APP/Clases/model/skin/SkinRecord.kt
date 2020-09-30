package com.example.skinscanner.model.skin

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "skin")
data class SkinRecord(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "imagePath") val imagePath: String,
    @ColumnInfo(name = "result") var result: String,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "annotations") var annotations: String
)