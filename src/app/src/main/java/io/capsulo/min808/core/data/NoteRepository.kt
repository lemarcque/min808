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

    fun getNotesBookmarked(): Single<List<NoteEntity>> = store.getAllBookmarked()

    fun getNote(id: Int): Single<NoteEntity> = store.getSingular(id)

    /**
     * Store data in database store.
     */
    fun insertNote(note: Note): Completable {
        // map text to [NoteEntity]
        val entity = NoteEntity(note.author, note.title, note.content, note.date, false)
        return store.storeSingular(entity)
    }

    fun updateNote(id: Int, title: String, content: String, isBookmarked: Boolean) = store.replaceSingular(id, title, content, isBookmarked)

    fun deleteNote(id: Int): Single<Int> = store.removeSingular(id)


}