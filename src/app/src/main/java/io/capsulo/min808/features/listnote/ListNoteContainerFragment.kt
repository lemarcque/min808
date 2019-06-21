package io.capsulo.min808.features.listnote

import android.app.Service
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.fragment.app.Fragment
import io.capsulo.min808.R
import io.capsulo.min808.core.navigation.Navigator
import kotlinx.android.synthetic.main.listnote_container_fragment.*


/**
 *  Display a list all notes stored.
 */
class ListNoteContainerFragment(private val viewModel: ListNoteViewModel) : Fragment() {

    // Variable
    val TAG: String = ListNoteContainerFragment::class.java.simpleName


    companion object {
        // Factory method
        fun newInstance(viewModel: ListNoteViewModel) = ListNoteContainerFragment(viewModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.listnote_container_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // configuration of app bar
        toolbar_listnote.inflateMenu(R.menu.appbar_listnote)
        // configuration of se  arch bar
        val searchView = (toolbar_listnote.menu.findItem(R.id.action_publish_insertnote).actionView as SearchView)
        searchView.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                override fun onQueryTextChange(s: String?): Boolean {
                    viewModel.getNotes(s!!)
                    return true
                }

                override fun onQueryTextSubmit(s: String?): Boolean {
                    // disable the keyboard
                    val imm = activity?.getSystemService(Service.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(searchView.windowToken, 0)
                    return true
                }
            })
        }

        // set tablayout
        viewpager_listnote.adapter = PagerAdapter(fragmentManager!!, viewModel)

        // handle click on fab
        fab_listnote.setOnClickListener { Navigator.showInsertNote(context!!) }
    }

    private fun getCurrentFragment(): ListNoteFragment {
        val adapter = viewpager_listnote.adapter as PagerAdapter
        return adapter.getCurrentFragmentInstance(viewpager_listnote) as ListNoteFragment
    }

    fun showSnackbarMessage(message: String) = getCurrentFragment().showSnackbarMessage(message)

    fun refreshList() = getCurrentFragment().refreshList()



}

