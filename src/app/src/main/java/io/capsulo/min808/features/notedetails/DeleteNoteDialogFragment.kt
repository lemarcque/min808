package io.capsulo.min808.features.notedetails

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import java.lang.IllegalStateException

/**
 * A holder that display a dialog window to confirm the delete of notes.
 */
class DeleteNoteDialogFragment : DialogFragment() {


    var listener: OnDeleteNoteClickListener? = null

    interface OnDeleteNoteClickListener {
        fun onDeleteConfirm()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {

            val builder = AlertDialog.Builder(it)
            builder.setMessage("Are you sure you want to delete this entry ?")
                .setPositiveButton("delete") { dialog, id ->
                    listener?.onDeleteConfirm()
                }
                .setNegativeButton("cancel") { _, _ ->  }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }


}