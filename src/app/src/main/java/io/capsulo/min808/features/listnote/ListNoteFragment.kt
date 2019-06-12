package io.capsulo.min808.features.listnote

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import io.capsulo.min808.R
import io.capsulo.min808.core.navigation.Navigator
import kotlinx.android.synthetic.main.listnote_fragment.*
import androidx.recyclerview.widget.ItemTouchHelper
import io.capsulo.min808.core.data.Note


/**
 *  Display a list all notes stored.
 */
class ListNoteFragment(val viewModel: ListNoteViewModel) : Fragment(), ListNoteAdapter.OnItemClickListener {

    // Variable
    val TAG: String = ListNoteFragment::class.java.simpleName
    lateinit var mAdapter: ListNoteAdapter

    companion object {
        // Factory method
        fun newInstance(viewModel: ListNoteViewModel) = ListNoteFragment(viewModel)
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getNotesLiveData().observe(this, Observer {
            val adapter = recyclerview_listnote.adapter as ListNoteAdapter
            adapter.update(it.toMutableList())
        })
    }

    private fun setInterface() {

        // configuration of app bar
        toolbar_listnote.inflateMenu(R.menu.appbar_listnote)

        // handle click on fab
        fab_listnote.setOnClickListener { Navigator.showInsertNote(context!!) }

        // config recycler view
        mAdapter = ListNoteAdapter(this@ListNoteFragment)
        recyclerview_listnote.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(context)
        }

        val callback = SimpleItemTouchHelperCallback(mAdapter, context)
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(recyclerview_listnote)
    }

    fun showSnackbarMessage(message: String) {
        val view = activity!!.findViewById<View>(android.R.id.content)
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
    }

    fun refreshList() = viewModel.getNotes()

    override fun onClick(id : Int) {
        val bundle = Bundle()
        bundle.putInt(getString(R.string.bundle_intent_id_note), id)
        Navigator.showNoteDetails(context!!, bundle)
    }

    override fun onItemDelete(position: Int) {
        // Show snackbar with action
        val view = activity!!.findViewById<View>(android.R.id.content)
        Snackbar
            .make(view, R.string.notedetails_note_deleted, Snackbar.LENGTH_SHORT)
            .setAction(R.string.notedetails_note_undo_delete) { mAdapter.update(position) }
            .setActionTextColor(Color.WHITE)
            .show()
    }

}