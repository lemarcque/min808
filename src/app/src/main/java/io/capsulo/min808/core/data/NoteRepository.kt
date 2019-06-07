package io.capsulo.min808.core.data

import io.capsulo.base.data.store.Store
import io.capsulo.min808.core.data.room.NoteEntity
import io.reactivex.Completable

/**
 * Interface that make abstraction to the multiple data sources access. (Data Source API)
 */
class NoteRepository(val store: Store<String, NoteEntity>) {



    /**
     * Store data in database store.
     **/
    fun insertNote(textContent: String) : Completable {
        // map text to [NoteEntity]
        val entity = NoteEntity(textContent, 0)
        return store.putSingular(entity)
    }

}