package io.capsulo.min808.core.navigation

import android.app.Activity
import android.content.Context
import android.os.Bundle
import io.capsulo.min808.features.insertnote.InsertNoteActivity
import io.capsulo.min808.features.listnote.ListNoteActivity
import io.capsulo.min808.features.notedetails.NoteDetailsActivity

/**
 * Class used to navigate trough the application.
 */
class Navigator {

    companion object {

        /**
         * Goes to the <Dashboard> screen.
         */
        fun showListNote(context: Context) = context.startActivity(ListNoteActivity.callingIntent(context))

        /**
         * Goes to the <InsertNote> screen.
         */
        fun showInsertNote(context: Context) {
            val activity = context as Activity
            activity.startActivityForResult(InsertNoteActivity.callingIntent(context), InsertNoteActivity.INSERT_NOTE_REQUEST)
        }

        fun showNoteDetails(context: Context, bundle: Bundle) {
            val intent = NoteDetailsActivity.callingIntent(context)
            intent.putExtras(bundle)
            val activity = context as Activity
            activity.startActivityForResult(intent, NoteDetailsActivity.UPDATE_NOTE_REQUEST)
        }

    }
}