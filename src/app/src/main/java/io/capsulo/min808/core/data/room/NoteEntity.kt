package io.capsulo.min808.core.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Todo : Add class description
 */
@Entity(tableName = "t_note")
data class NoteEntity(
    var content: String?,
    var date: Long? = null) {
    @PrimaryKey(autoGenerate = true) var id: Int? = null
}