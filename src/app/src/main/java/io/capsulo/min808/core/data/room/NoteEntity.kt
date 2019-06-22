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
    var date: Long?,
    var bookmark: Boolean // TODO : Replace with an id in table 't_category'
) {
    @PrimaryKey(autoGenerate = true) var id: Int? = null
}