package io.capsulo.min808.core.data

import io.capsulo.min808.core.data.room.NoteEntity
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Interface that make abstraction to the multiple data sources access. (Data Source API)
 */
class NoteRepository(private val store: DatabaseStore) {



    fun getNoteList(): Single<List<NoteEntity>> = store.getAll()

    fun getNoteListFilter(filter: String): Single<List<NoteEntity>> = store.getAllFilter(filter)

    fun getNote(id: Int): Single<NoteEntity> = store.getSingular(id)

    /**
     * Store data in database store.
     */
    fun insertNote(note: Note): Completable {
        // map text to [NoteEntity]
        val entity = NoteEntity(note.author, note.title, note.content, note.date)
        return store.storeSingular(entity)
    }

    fun updateNote(id: Int, title: String, content: String) = store.replaceSingular(id, title, content)

    fun deleteNote(id: Int): Single<Int> {
        // map Note object to [NoteEntity]
        return store.removeSingular(id)
    }

}