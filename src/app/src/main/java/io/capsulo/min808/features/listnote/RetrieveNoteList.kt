package io.capsulo.min808.features.listnote

import io.capsulo.min808.core.data.NoteRepository
import io.capsulo.min808.core.data.room.NoteEntity
import io.capsulo.min808.core.domain.ReactiveInteractor
import io.capsulo.min808.core.utils.CalendarUtils
import io.reactivex.Observable
import io.reactivex.Single
import polanski.option.Option

/**
 * Interactor for the use case of retrieving a list of notes.
 */
class RetrieveNoteList(private  val repository: NoteRepository):
        ReactiveInteractor.RetrieveInteractor<String, List<NoteEntity>> {

    override fun getBehaviorStream(params: Option<String>): Single<List<NoteEntity>> {
        var single: Single<List<NoteEntity>>? = null
        var filter: String
        params.ifSome {
            single = if(it.isEmpty())
                repository.getNoteList()
            else { filter = "%$it%"; repository.getNoteListFilter(filter) }
        }
        return single ?: Single.error(Throwable("The parameter 'filter' is required to perform a request"))
    }
/*{
        return Single.fromObservable {
            repository
                .getNoteList()
                .toObservable()
                .flatMap { Observable.fromIterable(it) }
                .map { Note(it.title, it.content, CalendarUtils.getHumanRedableDate(it.date)) }
                .toList()
        }
    }*/

}