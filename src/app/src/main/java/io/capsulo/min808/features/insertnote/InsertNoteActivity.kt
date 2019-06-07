package io.capsulo.min808.features.insertnote

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import io.capsulo.min808.R

/**
 * Responsible to displaying a text area to insert note.
 */
class InsertNoteActivity : AppCompatActivity() {


    companion object {
        fun callingIntent(context: Context) = Intent(context, InsertNoteActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setContentView(R.layout.base_activity)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.base_activity, InsertNoteFragment.newInstance())
            .commit()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = false


}