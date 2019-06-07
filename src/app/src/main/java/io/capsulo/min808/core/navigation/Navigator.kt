package io.capsulo.min808.core.navigation

import android.content.Context
import io.capsulo.min808.features.insertnote.InsertNoteActivity
import io.capsulo.min808.features.listnote.ListNoteActivity

/**
 * Class used to navigate trough the application.
 */
class Navigator {

    companion object {

        /**
         * Goes to the <Dashboard> screen.
         */
        fun showListNote(context: Context) {
            context.startActivity(ListNoteActivity.callingIntent(context))
        }

        /**
         * Goes to the <InsertNote> screen.
         */
        fun showInsertNote(context: Context) {
            context.startActivity(InsertNoteActivity.callingIntent(context))
        }
    }
}