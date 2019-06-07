package io.capsulo.min808.features.listnote

import io.capsulo.min808.core.data.NoteRepository
import io.capsulo.min808.core.domain.ReactiveInteractor
import io.reactivex.Single
import polanski.option.Option

/**
 * Interactor for the use case of saving note in database.
 */
class StoreNote(private val repository: NoteRepository) : ReactiveInteractor.SendInteractor<String, String> {


    val INPUT_EMPTY_ERROR = "Input text is empty."

    override fun getSingle(params: Option<String>): Single<String> {
        var s = ""
        params.ifSome { s = it}
        return if(s.isNotEmpty()) repository.insertNote(s).andThen(Single.just("Ok"))
            else Single.error(Throwable(INPUT_EMPTY_ERROR))

        // TODO : NPE
    }

}