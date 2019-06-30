package io.capsulo.min808.features.insertnote

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import io.capsulo.min808.R
import io.capsulo.min808.core.data.DatabaseStore
import io.capsulo.min808.core.data.NoteRepository

/**
 * Responsible to displaying a text area to insert note.
 */
class InsertNoteActivity : AppCompatActivity() {


    companion object {
        const val INSERT_NOTE_REQUEST = 1

        fun callingIntent(context: Context) = Intent(context, InsertNoteActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setContentView(R.layout.activity_base)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.base_activity, InsertNoteFragment.newInstance(
                InsertNoteViewModel(
                    StoreNote(
                        NoteRepository(DatabaseStore(this))
                    )
                )
            ))
            .commit()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = false

    override fun onBackPressed() {
        super.setResult(Activity.RESULT_CANCELED, Intent())
        super.finish()
    }


}