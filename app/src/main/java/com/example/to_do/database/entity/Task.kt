package com.example.to_do.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import  java.io.Serializable


@Entity
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo
    var title: String,
    @ColumnInfo
    var descreption: String,
    @ColumnInfo
    var date: Long,
    @ColumnInfo
    var isDone: Boolean = false,

    ): Serializable


