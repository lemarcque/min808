package io.capsulo.min808.features.listnote

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.capsulo.min808.R
import io.capsulo.min808.core.data.DatabaseStore
import io.capsulo.min808.core.data.NoteRepository
import io.capsulo.min808.features.insertnote.InsertNoteActivity
import io.capsulo.min808.features.notedetails.NoteDetailsActivity


/**
 * Responsible  for listing all notes stored.
 */
class ListNoteActivity : AppCompatActivity() {

    // Variable
    val TAG: String? = ListNoteActivity::class.simpleName
    val LISTENOTE_FRAGMENT_TAG = "LISTNOTE_FRAGMENT"


    companion object {
        fun callingIntent(context: Context) = Intent(context, ListNoteActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.setContentView(R.layout.base_activity)
        super.onCreate(savedInstanceState)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.base_activity, ListNoteFragment.newInstance(
                ListNoteViewModel(
                    RetrieveNoteList(
                        NoteRepository(DatabaseStore(this))
                    )
                )), LISTENOTE_FRAGMENT_TAG)
            .commit()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val fragment: ListNoteFragment? =
            supportFragmentManager.findFragmentByTag(LISTENOTE_FRAGMENT_TAG) as ListNoteFragment

       when(requestCode) {
           InsertNoteActivity.INSERT_NOTE_REQUEST -> {
               when(resultCode) {
                   Activity.RESULT_OK -> {
                       fragment?.showSnackbarMessage(getString(R.string.listnote_insert_message))
                       fragment?.refreshList()
                   }
               }
           }
           NoteDetailsActivity.DELETE_NOTE_REQUEST -> {
               when(resultCode) {
                   Activity.RESULT_OK -> {
                       fragment?.showSnackbarMessage(getString(R.string.listnote_delete_message))
                       fragment?.refreshList()
                   }
               }
           }
       }
    }

}