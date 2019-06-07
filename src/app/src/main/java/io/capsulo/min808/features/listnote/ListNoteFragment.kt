package io.capsulo.min808.features.listnote

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.capsulo.min808.R
import io.capsulo.min808.core.navigation.Navigator
import kotlinx.android.synthetic.main.listnote_fragment.*

/**
 *  Display a list all notes stored.
 */
class ListNoteFragment : Fragment() {

    // Variable
    val TAG: String = ListNoteFragment::class.java.simpleName

    companion object {
        fun newInstance() = ListNoteFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.listnote_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setInterface()
    }

    /**
     * Configuration of the user interface components.
     */
    private fun setInterface() {

        // click on fab
        fab_listnote.setOnClickListener { Navigator.showInsertNote(context!!) }
    }


}