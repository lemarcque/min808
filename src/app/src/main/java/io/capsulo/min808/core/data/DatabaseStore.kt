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
class DatabaseStore(private val context: Context) : ReactiveStore<Int, NoteEntity> {

    override fun getSingular(model: Int): Single<NoteEntity> = Min808Database.getDatabase(context).noteDao().getSingular(model)

    override fun getAll(): Single<List<NoteEntity>> = Min808Database.getDatabase(context).noteDao().getAll()

    override fun getAllFilter(filter: String): Single<List<NoteEntity>> = Min808Database.getDatabase(context).noteDao().getAllFilter(filter)

    override fun storeSingular(model: NoteEntity): Completable  = Min808Database.getDatabase(context).noteDao().storeSingular(model)

    override fun storeAll(modelList: List<NoteEntity>): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun removeSingular(model: Int): Single<Int> = Min808Database.getDatabase(context).noteDao().deleteSingular(model)

    fun replaceSingular(model: Int, title: String, content: String): Completable = Min808Database.getDatabase(context).noteDao().replaceSingular(model, title, content)

    override fun replaceAll(modelList: List<NoteEntity>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clear() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}