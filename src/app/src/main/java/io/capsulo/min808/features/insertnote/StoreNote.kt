package io.capsulo.min808.features.insertnote

import io.capsulo.min808.core.data.Note
import io.capsulo.min808.core.data.NoteRepository
import io.capsulo.min808.core.domain.ReactiveInteractor
import io.reactivex.Completable
import io.reactivex.Single
import polanski.option.Option
import java.util.*

/**
 * Interactor for the use case of saving note in database.
 */
class StoreNote(private val repository: NoteRepository): ReactiveInteractor.SendInteractor<String, String> {


    val INPUT_EMPTY_ERROR = "Input text is empty."
    val SEPARATOR = "\n"

    override fun getSingle(params: Option<String>): Completable {
        var raw = ""
        lateinit var note: Note

        params.ifSome {
            raw = it
            val list = it.split(SEPARATOR).toTypedArray().toMutableList()
            val title = list.removeAt(0)
            val content = list.toTypedArray().joinToString(SEPARATOR)
            val dateInMillis = Calendar.getInstance().timeInMillis

            // Create instance of  Note
            note = Note(title, content, dateInMillis)
        }

        return if(raw.isNotEmpty()) repository.insertNote(note)
            else Completable.error(Throwable(INPUT_EMPTY_ERROR))

        // TODO : NPE
    }

}