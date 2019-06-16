package io.capsulo.min808.features.notedetails

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.text.Spannable
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.SearchView
import androidx.core.text.clearSpans
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import io.capsulo.min808.R
import kotlinx.android.synthetic.main.appbar_title_notedetails.*
import kotlinx.android.synthetic.main.notedetails_fragment.*


/**
 * Allow to insert a new note in database.
 */
class NoteDetailsFragment(val viewModel: NoteDetailsViewModel) : Fragment(), DeleteNoteDialogFragment.OnDeleteNoteClickListener {


    // TODO : Temporary variable
    var BOOKMARKED: Boolean = false
    var idItemDisplayed: Int? =  null
    var contentItemDisplayed: String? =  null
    var lineNumberChar = mutableListOf<Int>()
    var pointer = 0

    /**
     * Define the sens for scorlling when searching a character or wird,
     */
    enum class Sens(val value: Int) {
        UP(-1),
        DOWN(1)
    }

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
                R.id.action_search_notedetails -> { switchMenu(); true; }
                R.id.action_edit_notedetails -> { true; }
                R.id.action_delete_notedetails -> { deleteNote(); true; }
                else -> false
            }
        }
        val layout = layoutInflater.inflate(R.layout.appbar_title_notedetails, null)
        toolbar_notedetails.addView(layout)
    }

    private fun getNote() {
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
        contentItemDisplayed = note.content
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

    private fun switchMenu() {
        // Remove old menu
        toolbar_notedetails.menu.clear()
        val v = toolbar_notedetails.findViewById<LinearLayout>(R.id.layout_title_appbar_notedetails)
        toolbar_notedetails.removeView(v)

        // Add new menu
        toolbar_notedetails.inflateMenu(R.menu.appbar_notedetails_search)
        val searchView = toolbar_notedetails.menu.findItem(R.id.action_search_notedetails).actionView as SearchView
        searchView.setIconifiedByDefault(true)
        searchView.isFocusable = true
        searchView.isIconified = false
        searchView.requestFocusFromTouch()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean { return true}

            override fun onQueryTextChange(s: String?): Boolean {
                highlight(s!!)
                return true
            }
        })


        toolbar_notedetails.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.action_search_up_notedetails -> { scrollToWord(Sens.UP);  true }
                R.id.action_search_down_notedetails -> { scrollToWord(Sens.DOWN); true }
                else -> false
            }
        }
    }

    private fun highlight(s: String) {
        val spannable = SpannableString(textview_content_notedetails.text)
        spannable.clearSpans()

        lineNumberChar.clear()

        var index = textview_content_notedetails.text.indexOf(s)
        var lineNumber = textview_content_notedetails.layout.getLineForOffset(index)

        if(index > -1 && s.isNotEmpty()) {

            while (index > -1) {
                lineNumberChar.add(lineNumber)

                spannable.setSpan(
                    BackgroundColorSpan(context!!.getColor(R.color.colorTextHightlight)),
                    index,
                    index + s.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )

                index += s.length

                textview_content_notedetails.text = spannable
                index = textview_content_notedetails.text.indexOf(s, index)
                lineNumber = textview_content_notedetails.layout.getLineForOffset(index)
            }

            // scroll to the first occurrences
            pointer = -1
            scrollToWord(Sens.DOWN)

        }else {
            textview_content_notedetails.text = spannable
        }
    }

    private fun scrollToWord(sens: Sens) {

        if(lineNumberChar.size > 0) {
            pointer += sens.value

            if (pointer > lineNumberChar.size - 1) pointer = 0
            else if(pointer < 0) pointer = lineNumberChar.size - 1

            val pos = lineNumberChar[pointer]
            val margin = 2
            val y = textview_content_notedetails.layout.getLineTop(pos + margin)
            scrollview_notedetails.smoothScrollTo(0, y)
        }
    }

    override fun onDeleteConfirm() {
        viewModel.deleteNote(idItemDisplayed!!)
    }


}
