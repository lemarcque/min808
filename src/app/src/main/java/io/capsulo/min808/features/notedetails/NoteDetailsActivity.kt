package io.capsulo.min808.features.notedetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.capsulo.min808.R
import io.capsulo.min808.core.data.DatabaseStore
import io.capsulo.min808.core.data.NoteRepository
import io.capsulo.min808.core.presentation.BaseFragment


/**
 * Responsible  for displaying note content.
 */
class NoteDetailsActivity : AppCompatActivity() {

    // Variable
    val TAG: String? = NoteDetailsActivity::class.simpleName

    companion object {
        const val DELETE_NOTE_REQUEST = 2

        fun callingIntent(context: Context) = Intent(context, NoteDetailsActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.setContentView(R.layout.base_activity)
        super.onCreate(savedInstanceState)

        val repository = NoteRepository(DatabaseStore(this))

        supportFragmentManager.beginTransaction().add(R.id.base_activity, NoteDetailsFragment.newInstance(
            NoteDetailsViewModel(
                RetrieveNote(repository),
                DeleteNote(repository)
            )
        )).commit()
    }

    override fun onBackPressed() {

        val fragmentList = supportFragmentManager.fragments
        var handled = false

        for(fragment in fragmentList) {
            if(fragment is BaseFragment) handled = fragment.onBackPressed()
            if(handled) break
        }

        if(!handled) super.onBackPressed()

    }

}