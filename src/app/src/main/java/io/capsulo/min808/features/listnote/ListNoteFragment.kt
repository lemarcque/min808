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
import com.google.android.material.snackbar.Snackbar
import io.capsulo.min808.R
import io.capsulo.min808.R.*
import io.capsulo.min808.core.navigation.Navigator
import kotlinx.android.synthetic.main.listnote_fragment.*

/**
 * Todo : Add class description
 */
class ListNoteFragment(private val viewModel: ListNoteViewModel) : Fragment(), ListNoteAdapter.OnItemClickListener {


    lateinit var mAdapter: ListNoteAdapter

    companion object {
        fun newInstance(position: Int, viewModel: ListNoteViewModel): ListNoteFragment {
            val bundle = Bundle()
            bundle.putInt("position", position)

            val fragment = ListNoteFragment(viewModel)
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
        println("message => $message")
        println("activity => $activity")
        /*val view = activity!!.findViewById<View>(android.R.id.content)
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()*/
    }

    fun refreshList() = viewModel.getNotes(getString(R.string.empty))

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