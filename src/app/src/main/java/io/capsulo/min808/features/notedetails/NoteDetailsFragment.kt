package io.capsulo.min808.features.notedetails

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import io.capsulo.min808.R
import io.capsulo.min808.features.listnote.NoteView
import kotlinx.android.synthetic.main.notedetails_fragment.*


/**
 * Allow to insert a new note in database.
 */
class NoteDetailsFragment(val viewModel: NoteDetailsViewModel) : Fragment() {


    // TODO : Temporary variable
    var BOOKMARKED: Boolean = false

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
                R.id.action_bookmark_notedetails -> {
                    if(BOOKMARKED) {
                        it.setIcon(R.drawable.ic_bookmark_border_black_24dp)
                    } else {
                        it.setIcon(R.drawable.ic_bookmark_black_24dp)
                    }

                    BOOKMARKED = !BOOKMARKED
                    true
                }
                else -> { false }
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

    fun setNote(note: NoteView) {
        println(note)
        textview_title_notedetails.text = Html.fromHtml(note.title, Html.FROM_HTML_MODE_COMPACT)
        textview_content_notedetails.text = note.content
    }

}
