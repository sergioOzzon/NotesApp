package ru.sergioozzon.kotlin.notesapp.data.entity

import java.io.Serializable
import java.util.*

data class Note(
    val id: String = "",
    val title: String = "",
    val text: String = "",
    val color: Color = Color.WHITE,
    val lastChanged: Date = Date()
) : Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Note

        if (id != other.id) return false
        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + text.hashCode()
        result = 31 * result + color.hashCode()
        return result
    }

    enum class Color {
        WHITE,
        YELLOW,
        GREEN,
        BLUE,
        RED,
        VIOLET,
        PINK
    }
}