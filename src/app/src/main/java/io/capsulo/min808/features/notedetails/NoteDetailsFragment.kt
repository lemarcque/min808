package io.capsulo.min808.features.notedetails

import android.app.Activity
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import io.capsulo.min808.R
import io.capsulo.min808.features.listnote.NoteView
import kotlinx.android.synthetic.main.actionbar_title_test.*
import kotlinx.android.synthetic.main.notedetails_fragment.*


/**
 * Allow to insert a new note in database.
 */
class NoteDetailsFragment(val viewModel: NoteDetailsViewModel) : Fragment(), DeleteNoteDialogFragment.OnDeleteNoteClickListener {


    // TODO : Temporary variable
    var BOOKMARKED: Boolean = false
    var idItemDisplayed: Int? =  null

    companion object {
        // Factory method

        fun newInstance(viewModel: NoteDetailsViewModel) = NoteDetailsFragment(viewModel)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getNoteLiveData().observe(this, Observer { setNote(it) })
        viewModel.getNoteDeletedLiveData().observe(this, Observer {
            activity!!.setResult(Activity.RESULT_OK);
            activity?.finish() })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.notedetails_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setInterface()
        getNote()
    }

    private fun setInterface() {
        // configuration of app bar
        toolbar_notedetails.inflateMenu(R.menu.appbar_notedetails)
        toolbar_notedetails.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)
        toolbar_notedetails.setNavigationOnClickListener { activity?.finish() }
        toolbar_notedetails.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.action_bookmark_notedetails -> { bookmarkNote(it); true }
                R.id.action_edit_notedetails -> { true; }
                R.id.action_delete_notedetails -> { deleteNote(); true; }
                else -> false
            }
        }
        val layout = layoutInflater.inflate(R.layout.actionbar_title_test, null)
        toolbar_notedetails.addView(layout)
    }

    fun getNote() {
        // Retrieve note
        val id: Int = activity?.intent?.getIntExtra(getString(R.string.bundle_intent_id_note), -1) ?: -1
        if(id > -1) viewModel.getNote(id)
        else activity!!.finish()
    }

    private fun setNote(note: NoteDetailsView) {
        idItemDisplayed = note.id
        textview_author_appbar_notedetails.text = note.author
        textview_date_appbar_notedetails.text = note.date
        textview_title_notedetails.text = Html.fromHtml(note.title, Html.FROM_HTML_MODE_COMPACT)
        textview_content_notedetails.text = note.content
    }

    // TODO : Call ViewModel's method
    private fun bookmarkNote(item: MenuItem) {
        if(BOOKMARKED) {
            item.setIcon(R.drawable.ic_bookmark_border_black_24dp)
        } else {
            item.setIcon(R.drawable.ic_bookmark_black_24dp)
        }

        BOOKMARKED = !BOOKMARKED
    }

    private fun deleteNote() {
        val dialogFragment = DeleteNoteDialogFragment()
        dialogFragment.listener = this
        dialogFragment.show(fragmentManager, "deleteNoteDialog")
    }

    override fun onDeleteConfirm() {
        viewModel.deleteNote(idItemDisplayed!!)
    }

}
