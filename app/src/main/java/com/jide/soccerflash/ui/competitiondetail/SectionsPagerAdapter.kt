package com.jide.soccerflash.ui.competitiondetail

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.jide.soccerflash.R
import com.jide.soccerflash.ui.competitiondetail.standings.StandingsFragment
import com.jide.soccerflash.ui.competitiondetail.teams.TeamsFragment
import com.jide.soccerflash.ui.home.todaysfixtures.FixturesFragment

private val TAB_TITLES = arrayOf(
    R.string.tab_text_1,
    R.string.tab_text_2,
    R.string.tab_text_3
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(
    private val context: Context,
    fm: FragmentManager,
    private val comp_id: Int
) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> StandingsFragment.newInstance(comp_id)
            1 -> FixturesFragment.newInstance(comp_id)
            2 -> TeamsFragment.newInstance(comp_id)
            else -> StandingsFragment.newInstance(comp_id)
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount() = 3
}