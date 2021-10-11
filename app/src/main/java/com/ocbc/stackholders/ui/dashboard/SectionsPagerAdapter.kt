package com.ocbc.stackholders.ui.dashboard

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.ocbc.stackholders.R
import com.ocbc.stackholders.ui.dashboard.home.HomeFragment
import com.ocbc.stackholders.ui.dashboard.payee.PayeeFragment

private val TAB_TITLES = arrayOf(
    R.string.tab_home,
    R.string.tab_payee,
    R.string.tab_settings
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        if(position == 0)
            return HomeFragment.newInstance()
        else if(position == 1)
            return PayeeFragment.newInstance(position + 1)
        else
            return PayeeFragment.newInstance(position + 1)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        // Show 2 total pages.
        return 3
    }
}