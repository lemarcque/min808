package io.capsulo.min808.core.data

import android.content.Context
import io.capsulo.base.data.store.Store
import io.capsulo.min808.core.data.room.Min808Database
import io.capsulo.min808.core.data.room.NoteEntity
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

/**
 * Store that hold data in a SQLLite database.
 */
class DatabaseStore(private val context: Context) : Store<String, NoteEntity> {



    override fun getSingular(key: String): Maybe<NoteEntity> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAll(): Maybe<List<NoteEntity>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    override fun putAll(valueList: List<NoteEntity>) = Min808Database.getDatabase(context).noteDao().storeAll(valueList)


    override fun clear() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun putSingular(value: NoteEntity) = Min808Database.getDatabase(context).noteDao().storeSingular(value)


}