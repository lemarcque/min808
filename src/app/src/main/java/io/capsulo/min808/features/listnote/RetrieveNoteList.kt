package io.capsulo.min808.features.listnote

import io.capsulo.min808.core.data.NoteRepository
import io.capsulo.min808.core.data.room.NoteEntity
import io.capsulo.min808.core.domain.ReactiveInteractor
import io.capsulo.min808.core.utils.CalendarUtils
import io.reactivex.Observable
import io.reactivex.Single
import polanski.option.Option

/**
 * Todo : Add class description
 */
class RetrieveNoteList(private  val repository: NoteRepository):
        ReactiveInteractor.RetrieveInteractor<Option<Void>, List<NoteEntity>> {



    override fun getBehaviorStream(params: Option<Void>): Single<List<NoteEntity>> = repository.getNoteList()
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