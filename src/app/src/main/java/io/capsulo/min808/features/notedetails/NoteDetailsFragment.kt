package io.capsulo.min808.features.notedetails

import android.app.Activity
import android.os.Bundle
import android.text.*
import android.text.style.BackgroundColorSpan
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.Toolbar
import androidx.core.text.clearSpans
import androidx.lifecycle.Observer
import io.capsulo.min808.R
import io.capsulo.min808.core.presentation.BaseFragment
import kotlinx.android.synthetic.main.appbar_title_notedetails.*
import kotlinx.android.synthetic.main.notedetails_fragment.*


/**
 * Allow to insert a new note in database.
 */
class NoteDetailsFragment(val viewModel: NoteDetailsViewModel) : BaseFragment(), DeleteNoteDialogFragment.OnDeleteNoteClickListener {


    // TODO : Temporary variable
    var BOOKMARKED: Boolean = false
    var idItemDisplayed: Int? =  null
    var contentItemDisplayed: String? =  null
    var lineNumberChar = mutableListOf<Int>()
    var pointer = 0
    var inSearchMode = Mode.VIEW

    /**
     * Define the sens for scrolling when searching a character or word,
     */
    enum class Sens(val value: Int) {
        UP(-1),
        DOWN(1)
    }

    enum class Mode(val value: Int) {
        VIEW(1),
        SEARCH(2),
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
            activity!!.setResult(Activity.RESULT_OK)
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
        displayNormalMenu()
    }

    private fun displaySearchMenu() {
        // Remove old menu
        toolbar_notedetails.menu.clear()
        val v = toolbar_notedetails.findViewById<LinearLayout>(R.id.layout_title_appbar_notedetails)
        toolbar_notedetails.removeView(v)

        // Add new menu
        toolbar_notedetails.inflateMenu(R.menu.appbar_notedetails_search)
        val searchLayout = layoutInflater.inflate(R.layout.appbar_search_notedetails, null)
        val params = Toolbar.LayoutParams(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.WRAP_CONTENT)
        toolbar_notedetails.addView(searchLayout, params)

        // handle text changing
        val editText = toolbar_notedetails.findViewById<EditText>(R.id.edittext_search_appbar_notedetails)
        editText.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {  highlight(s.toString()) }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun afterTextChanged(s: Editable?) {

            }
        })
        editText.requestFocus()
        inSearchMode = Mode.SEARCH

        toolbar_notedetails.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.action_search_up_notedetails -> { scrollNext(Sens.UP);  true }
                R.id.action_search_down_notedetails -> { scrollNext(Sens.DOWN); true }
                else -> false
            }
        }
    }

    private fun displayNormalMenu() {
        // Remove old menu
        toolbar_notedetails.menu.clear()
        val v = toolbar_notedetails.findViewById<FrameLayout>(R.id.layout_search_appbar_notedetails)
        toolbar_notedetails.removeView(v)

        // Add new menu
        // configuration of app bar
        toolbar_notedetails.inflateMenu(R.menu.appbar_notedetails)
        toolbar_notedetails.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)
        toolbar_notedetails.setNavigationOnClickListener { activity?.finish() }
        toolbar_notedetails.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.action_bookmark_notedetails -> { bookmarkNote(it); true }
                R.id.action_search_notedetails -> { displaySearchMenu(); true; }
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


    private fun highlight(s: String) {
        clearHighlight()

        val spannable = SpannableString(textview_content_notedetails.text)
        var index = textview_content_notedetails.text.indexOf(s)
        var lineNumber = textview_content_notedetails.layout.getLineForOffset(index)

        // Word founded
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
            scrollNext(Sens.DOWN)

        }
        // No word found
        else {
            clearHighlight()
            scrollview_notedetails.smoothScrollTo(0, 0)
        }
    }

    private fun clearHighlight() {
        val spannable = SpannableString(textview_content_notedetails.text)
        spannable.clearSpans()
        lineNumberChar.clear()
        textview_content_notedetails.text = spannable
    }

    private fun scrollNext(sens: Sens) {

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

    override fun onBackPressed(): Boolean {
        when(inSearchMode) {
            Mode.SEARCH -> {
                inSearchMode = Mode.VIEW

                val editText = toolbar_notedetails.findViewById<EditText>(R.id.edittext_search_appbar_notedetails)
                editText.clearFocus()
                scrollview_notedetails.smoothScrollTo(0, 0)
                clearHighlight()
                displayNormalMenu()
            }
            Mode.VIEW ->  activity!!.finish()
        }
        return true
    }


}
