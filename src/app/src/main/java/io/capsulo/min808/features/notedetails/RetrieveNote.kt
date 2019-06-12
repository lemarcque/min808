package io.capsulo.min808.features.notedetails

import io.capsulo.min808.core.data.NoteRepository
import io.capsulo.min808.core.data.room.NoteEntity
import io.capsulo.min808.core.domain.ReactiveInteractor
import io.capsulo.min808.core.utils.CalendarUtils
import io.reactivex.Observable
import io.reactivex.Single
import polanski.option.Option
import kotlin.math.sin

/**
 * Get a no from his id
 */
class RetrieveNote(private  val repository: NoteRepository):
        ReactiveInteractor.RetrieveInteractor<Int, NoteEntity> {


    override fun getBehaviorStream(params: Option<Int>): Single<NoteEntity> {
        var single: Single<NoteEntity>? = null
        params.ifSome { single = repository.getNote(it) }
        return single ?: Single.error(Throwable("The parameter 'id' is required to perform a request"))
    }

}