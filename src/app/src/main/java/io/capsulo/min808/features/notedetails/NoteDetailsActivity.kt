package io.capsulo.min808.features.notedetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.capsulo.min808.R
import io.capsulo.min808.core.data.DatabaseStore
import io.capsulo.min808.core.data.NoteRepository


/**
 * Responsible  for displaying note content.
 */
class NoteDetailsActivity : AppCompatActivity() {

    // Variable
    val TAG: String? = NoteDetailsActivity::class.simpleName

    companion object {
        fun callingIntent(context: Context) = Intent(context, NoteDetailsActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.setContentView(R.layout.base_activity)
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction().add(R.id.base_activity, NoteDetailsFragment.newInstance(
            NoteDetailsViewModel(
                RetrieveNote(
                    NoteRepository(
                        DatabaseStore(this)
                    )
                )
            )
        )).commit()
    }

}