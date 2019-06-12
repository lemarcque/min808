package io.capsulo.min808.core.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Define the structure of the table 't_note' in database,
 */
@Entity(tableName = "t_note")
data class NoteEntity(
    val author: String?,
    val title: String?,
    var content: String?,
    var date: Long?) {
    @PrimaryKey(autoGenerate = true) var id: Int? = null
}