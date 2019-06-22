package io.capsulo.min808.features.notedetails

import io.capsulo.min808.core.data.NoteRepository
import io.capsulo.min808.core.domain.ReactiveInteractor
import io.reactivex.Completable
import polanski.option.Option

/**
 * Update a note from his id.
 */
class UpdateNote(private  val repository: NoteRepository):
        ReactiveInteractor.SendInteractor<NoteDetailsView, Void> {

    override fun getSingle(params: Option<NoteDetailsView>): Completable {
        var completable: Completable? = null
        params.ifSome {
            completable = repository.updateNote(it.id!!, it.title!!, it.content!!, it.bookmarked!!) }
        return completable ?: Completable.error(Throwable("The parameter 'NoteDetailsView' is required to perform a request"))
    }
}