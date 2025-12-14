package com.example.myhomework2

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class Habit(
    val habitName: String,
    val description: String,
    val priority: String,
    val type: String,
    val howMany: String,
    val period: String,
    val color: Int
) : Parcelable

