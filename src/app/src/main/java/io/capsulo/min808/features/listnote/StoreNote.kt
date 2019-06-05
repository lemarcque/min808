package io.capsulo.min808.features.listnote

import io.capsulo.min808.core.data.NoteRepository
import io.capsulo.min808.core.domain.ReactiveInteractor
import io.reactivex.Single
import polanski.option.Option

/**
 * Interactor for the use case of saving note in database.
 */
class StoreNote : ReactiveInteractor.SendInteractor<String, String> {

    // Temp
    private val repository: NoteRepository =
        NoteRepository()


    override fun getSingle(params: Option<String>): Single<String> {
        params.ifSome { repository.fetchUserDraft(it) }
        return Single.just("The input text is empty.")
    }

}