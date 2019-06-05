package io.capsulo.min808.core.data

import io.reactivex.Single

/**
 * Interface that make abstraction to the multiple data sources access. (Data Source API)
 */
class NoteRepository {

    /**
     * Store data in database store.
     **/
    fun fetchUserDraft(textContent: String) : Single<String> {
        return Single.just("Note saved.")
    }

}