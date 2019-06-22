package io.capsulo.min808.features.listnote

import android.app.Service
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import io.capsulo.min808.R
import io.capsulo.min808.core.navigation.Navigator
import kotlinx.android.synthetic.main.listnote_container_fragment.*
import java.io.Serializable


/**
 *  Display a list all notes stored.
 */
class ListNoteContainerFragment(private val viewModel: ListNoteViewModel) : Fragment(), ListNoteFragment.OnScrollListener {

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
        // configuration of search bar
        val searchView = (toolbar_listnote.menu.findItem(R.id.action_publish_insertnote).actionView as SearchView)
        searchView.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                override fun onQueryTextChange(s: String?): Boolean {
                    viewModel.getAllNotes(s!!)
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
        viewpager_listnote.adapter = PagerAdapter(fragmentManager!!, viewModel, this)
        viewpager_listnote.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            /**
             * Called when the scroll state changes. Useful for discovering when the user
             * begins dragging, when the pager is automatically settling to the current page,
             * or when it is fully stopped/idle.
             *
             * @param state The new scroll state.
             * @see ViewPager.SCROLL_STATE_IDLE
             *
             * @see ViewPager.SCROLL_STATE_DRAGGING
             *
             * @see ViewPager.SCROLL_STATE_SETTLING
             */
            override fun onPageScrollStateChanged(state: Int) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            /**
             * This method will be invoked when the current page is scrolled, either as part
             * of a programmatically initiated smooth scroll or a user initiated touch scroll.
             *
             * @param position Position index of the first page currently being displayed.
             * Page position+1 will be visible if positionOffset is nonzero.
             * @param positionOffset Value from [0, 1) indicating the offset from the page at position.
             * @param positionOffsetPixels Value in pixels indicating the offset from position.
             */
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            /**
             * This method will be invoked when a new page becomes selected. Animation is not
             * necessarily complete.
             *
             * @param position Position index of the new selected page.
             */
            override fun onPageSelected(position: Int) {
                refreshList()
            }


        })

        // handle click on fab
        fab_listnote.setOnClickListener { Navigator.showInsertNote(context!!) }
    }

    private fun getCurrentFragment(): ListNoteFragment {
        val adapter = viewpager_listnote.adapter as PagerAdapter
        return adapter.getCurrentFragmentInstance(viewpager_listnote) as ListNoteFragment
    }

    fun showSnackbarMessage(message: String) = getCurrentFragment().showSnackbarMessage(message)

    fun refreshList() = getCurrentFragment().refreshList()

    override fun onPageScrolled(hide: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onListScrolled(hide: Boolean) {
        if(hide) fab_listnote.hide()
        else fab_listnote.show()
    }

}

