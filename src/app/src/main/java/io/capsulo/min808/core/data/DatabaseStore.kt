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

    fun getAllBookmarked(): Single<List<NoteEntity>> = Min808Database.getDatabase(context).noteDao().getAllBookarmked()

    override fun storeSingular(model: NoteEntity): Completable  = Min808Database.getDatabase(context).noteDao().storeSingular(model)

    override fun storeAll(modelList: List<NoteEntity>): Completable = Completable.error(Throwable("Features not implemented"))

    fun removeSingular(model: Int): Single<Int> = Min808Database.getDatabase(context).noteDao().deleteSingular(model)

    fun replaceSingular(model: Int, title: String, content: String, isBookmarked: Boolean): Completable = Min808Database.getDatabase(context).noteDao().replaceSingular(model, title, content, isBookmarked)

    override fun replaceAll(modelList: List<NoteEntity>) = error("Features not implemented")

    override fun clear() = error("Features not imple")


}