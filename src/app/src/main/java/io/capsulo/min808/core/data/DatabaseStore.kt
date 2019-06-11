package io.capsulo.min808.core.data

import android.content.Context
import io.capsulo.base.data.store.ReactiveStore
import io.capsulo.base.data.store.Store
import io.capsulo.min808.core.data.room.Min808Database
import io.capsulo.min808.core.data.room.NoteEntity
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Store that hold data in a SQLLite database.
 */
class DatabaseStore(private val context: Context) : ReactiveStore<String, NoteEntity> {


    override fun getSingular(model: String): Observable<NoteEntity> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAll(): Single<List<NoteEntity>> = Min808Database.getDatabase(context).noteDao().getAll()

    override fun storeSingular(model: NoteEntity): Completable  = Min808Database.getDatabase(context).noteDao().storeSingular(model)

    override fun storeAll(modelList: List<NoteEntity>): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun replaceAll(modelList: List<NoteEntity>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clear() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}