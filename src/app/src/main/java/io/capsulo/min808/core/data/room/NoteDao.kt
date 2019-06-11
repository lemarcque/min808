package io.capsulo.min808.core.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Single

@Dao
abstract class NoteDao {

    @Query("SELECT * FROM t_note")
    abstract fun getAll(): Single<List<NoteEntity>>

    @Query("SELECT * FROM t_note WHERE id =:id")
    abstract fun getSingular(id: Int): Single<NoteEntity>

    @Insert
    abstract fun storeAll(notes: List<NoteEntity>): Completable

    @Insert
    abstract fun storeSingular(note: NoteEntity): Completable

    @Delete
    abstract fun deleteSingular(note: NoteEntity): Completable
}
