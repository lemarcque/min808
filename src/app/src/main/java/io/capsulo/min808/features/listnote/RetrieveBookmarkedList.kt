package io.capsulo.min808.features.listnote

import io.capsulo.min808.core.data.NoteRepository
import io.capsulo.min808.core.data.room.NoteEntity
import io.capsulo.min808.core.domain.ReactiveInteractor
import io.reactivex.Single
import polanski.option.Option

/**
 * Interactor for the use case of retrieving a list of notes.
 */
class RetrieveBookmarkedList(private  val repository: NoteRepository):
    ReactiveInteractor.RetrieveInteractor<Void, List<NoteEntity>> {

    override fun getBehaviorStream(params: Option<Void>): Single<List<NoteEntity>> {
        val single: Single<List<NoteEntity>>? = repository.getNotesBookmarked()
        return single
            ?: Single.error(Throwable("The parameter 'filter' is required to perform a request"))
    }
}