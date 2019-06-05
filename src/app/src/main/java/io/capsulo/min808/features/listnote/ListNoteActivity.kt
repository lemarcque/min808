package io.capsulo.min808.features.listnote

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.capsulo.min808.R


/**
 * Responsible  for listing all notes stored.
 */
class ListNoteActivity : AppCompatActivity() {

    // Variable
    val TAG: String? = ListNoteActivity::class.simpleName

    companion object {
        fun callingIntent(context: Context) = Intent(context, ListNoteActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.setContentView(R.layout.base_activity)
        super.onCreate(savedInstanceState)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.base_activity, ListNoteFragment.newInstance())
            .commit()
    }

}