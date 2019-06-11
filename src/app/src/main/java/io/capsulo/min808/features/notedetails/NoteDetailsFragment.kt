package io.capsulo.min808.features.notedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.capsulo.min808.R
import kotlinx.android.synthetic.main.notedetails_fragment.*


/**
 * Allow to insert a new note in database.
 */
class NoteDetailsFragment : Fragment() {


    // TODO : Temporary variable
    var BOOKMARKED: Boolean = false

    companion object {
        // Factory method
        fun newInstance() = NoteDetailsFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

}
