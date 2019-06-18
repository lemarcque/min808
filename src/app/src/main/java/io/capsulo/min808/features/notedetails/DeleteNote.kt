package io.capsulo.min808.features.notedetails

import io.capsulo.min808.core.data.NoteRepository
import io.capsulo.min808.core.domain.ReactiveInteractor
import io.reactivex.Single
import polanski.option.Option

/**
 * Delete a note from his id
 */
class DeleteNote(private  val repository: NoteRepository):
        ReactiveInteractor.DeleteInteractor<Int, Int> {

    override fun getSingle(params: Option<Int>): Single<Int> {
        var single: Single<Int>? = null
        params.ifSome { single = repository.deleteNote(it) }
        return single ?: Single.error(Throwable("The parameter 'id' is required to perform a request"))
    }
}