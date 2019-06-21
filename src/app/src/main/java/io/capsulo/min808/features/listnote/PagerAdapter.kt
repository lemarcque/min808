package io.capsulo.min808.features.listnote

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager


/**
 * Populate pages inside of a [ViewPager]
 */
class PagerAdapter(val fm: FragmentManager, private val viewModel: ListNoteViewModel) : FragmentPagerAdapter(fm) {

    private val tabTitle = listOf("All", "Bookmark")

    override fun getPageTitle(position: Int): CharSequence? = tabTitle[position]
    override fun getItem(position: Int): Fragment =
        ListNoteFragment.newInstance(position, viewModel)
    override fun getCount(): Int = tabTitle.size


    fun getCurrentFragmentInstance (viewPager: ViewPager): Fragment? {
        return fm.findFragmentByTag("android:switcher:" +
                io.capsulo.min808.R.id.viewpager_listnote +
                ":" + viewPager.currentItem)
    }

}