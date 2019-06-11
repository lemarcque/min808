package io.capsulo.min808.core.data

import io.capsulo.base.data.store.ReactiveStore
import io.capsulo.min808.core.data.room.NoteEntity
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Interface that make abstraction to the multiple data sources access. (Data Source API)
 */
class NoteRepository(val store: ReactiveStore<String, NoteEntity>) {



    fun getNoteList(): Single<List<NoteEntity>> = store.getAll()

    /**
     * Store data in database store.
     */
    fun insertNote(note: Note): Completable {
        // map text to [NoteEntity]
        val entity = NoteEntity(note.title, note.content, note.date)
        return store.storeSingular(entity)
    }

}