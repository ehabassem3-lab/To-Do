package com.example.to_do.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class Task(
@PrimaryKey(autoGenerate = true)
val id : Int,
@ColumnInfo
val title : String,
@ColumnInfo
val descreption : String,
@ColumnInfo
val date : Int,
@ColumnInfo
val isDone : Boolean

)


