package io.capsulo.min808.core.data.room

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single

@Dao
abstract class NoteDao {

    @Query("SELECT * FROM t_note ORDER BY date DESC")
    abstract fun getAll(): Single<List<NoteEntity>>

    @Query("SELECT * FROM t_note WHERE title LIKE :filter ORDER BY date DESC")
    abstract fun getAllFilter(filter: String): Single<List<NoteEntity>>

    @Query("SELECT * FROM t_note WHERE id =:id")
    abstract fun getSingular(id: Int): Single<NoteEntity>

    @Insert
    abstract fun storeAll(notes: List<NoteEntity>): Completable

    @Insert
    abstract fun storeSingular(note: NoteEntity): Completable

    @Query("UPDATE t_note SET content=:content, title=:title WHERE id=:id")
    abstract fun replaceSingular(id: Int, title:String, content: String): Completable

    @Query("DELETE FROM t_note WHERE id=:id")
    abstract fun deleteSingular(id: Int): Single<Int>
}
