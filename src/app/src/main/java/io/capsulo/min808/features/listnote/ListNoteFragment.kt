package io.capsulo.min808.features.listnote

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import io.capsulo.min808.R
import io.capsulo.min808.R.*
import io.capsulo.min808.core.navigation.Navigator
import kotlinx.android.synthetic.main.listnote_container_fragment.*
import kotlinx.android.synthetic.main.listnote_fragment.*
import java.io.Serializable

/**
 * Responsible for displaying a list of notes.
 */
class ListNoteFragment(private val viewModel: ListNoteViewModel, val listener: OnScrollListener) : Fragment(), ListNoteAdapter.OnItemClickListener {


    lateinit var mAdapter: ListNoteAdapter
    private var position: Int? = null
    // TODO private var listener: OnScrollListener? = null

    interface OnScrollListener : Serializable {
        fun onListScrolled(hide: Boolean)
        fun onPageScrolled(hide: Boolean)
    }

    companion object {
        fun newInstance(position: Int, viewModel: ListNoteViewModel, listener: OnScrollListener): ListNoteFragment {
            val bundle = Bundle()
            bundle.putInt("position", position)

            val fragment = ListNoteFragment(viewModel, listener)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layout.listnote_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.notesLiveData?.observe(this, Observer {
            val adapter = recyclerview_listnote.adapter as ListNoteAdapter
            adapter.update(it.toMutableList())
        })
        viewModel.notesDeletedLiveData?.observe(this, Observer { refreshList() })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setInterface()
    }

    private fun setInterface() {
        // Set position properties
        position = arguments?.getInt("position", -1)

        // config recycler view
        mAdapter = ListNoteAdapter(this@ListNoteFragment)
        recyclerview_listnote.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(context)
        }

        val callback = SimpleItemTouchHelperCallback(mAdapter, context)
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(recyclerview_listnote)

        // Hide FAB while scrolling
        // TODO listener = arguments?.getSerializable("scrollListener") as OnScrollListener

        recyclerview_listnote.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if(dy > 0) listener?.onListScrolled(true)
                else listener?.onListScrolled(false)
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    fun showSnackbarMessage(message: String) {
        val view = activity!!.findViewById<View>(android.R.id.content)
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
    }

    fun refreshList() {
        if(position == 0) viewModel.getAllNotes(getString(R.string.empty))
        else if (position == 1) viewModel.getNotesBookmaked()
    }

    override fun onClick(id : Int) {
        val bundle = Bundle()
        bundle.putInt(getString(R.string.bundle_intent_id_note), id)
        Navigator.showNoteDetails(context!!, bundle)
    }

    override fun onItemDelete(id: Int) {
        // Show snackbar with action
        val view = activity!!.findViewById<View>(android.R.id.content)
        Snackbar
            .make(view, R.string.notedetails_note_deleted, Snackbar.LENGTH_SHORT)
            .setAction(R.string.notedetails_note_undo_delete) { mAdapter.update(id) }
            .setActionTextColor(Color.WHITE)
            .addCallback(object : Snackbar.Callback() {
                override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                    if (event == DISMISS_EVENT_TIMEOUT) {
                        // Snackbar closed on its own
                        viewModel.deleteNote(id)
                    }
                }
            })
            .show()
    }

}