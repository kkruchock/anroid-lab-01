package com.example.androidlab01.homework_5.movieBase.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val login: String,
    val email: String,
    val passwordHash: String
)

