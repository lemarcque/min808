package io.capsulo.min808.core.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Database holder extending[RoomDatabase]
 */
@Database(entities = [NoteEntity::class], version = 1, exportSchema = false)
abstract class Min808Database : RoomDatabase() {

    abstract fun noteDao() : NoteDao

    companion object {

        @Volatile
        private var INSTANCE : Min808Database? = null
        private var DB_NAME: String  = "min808_database"

        fun getDatabase(context: Context): Min808Database {
            val tempInstance = INSTANCE
            if(tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    Min808Database::class.java,
                    DB_NAME
                )
                    // Wipes and rebuilds instead of migrating
                    // if no Migration object.
                    // Migration is not part of this practical.
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                return instance
            }
        }
    }
}