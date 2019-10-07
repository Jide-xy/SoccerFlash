package com.jide.soccerflash.ui.competitiondetail.teams

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.jide.soccerflash.R
import com.jide.soccerflash.api.Resource
import com.jide.soccerflash.api.Status
import com.jide.soccerflash.di.Injectable
import com.jide.soccerflash.models.Team
import com.jide.soccerflash.ui.competitiondetail.standings.StandingsFragment.Companion.ARG_COMPETITION_ID
import kotlinx.android.synthetic.main.fragment_teams.*
import kotlinx.android.synthetic.main.layout_empty_layout.*
import javax.inject.Inject

class TeamsFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val teamsViewModel by viewModels<TeamsViewModel> {
        viewModelFactory
    }

    private var competitionID: Int? = null

    private var adapter = TeamAdapter()

    companion object {

        @JvmStatic
        fun newInstance(competitionId: Int): TeamsFragment {
            return TeamsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COMPETITION_ID, competitionId)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        competitionID = arguments?.getInt(ARG_COMPETITION_ID)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        teamsList.adapter = adapter
        teamsViewModel.teamsLiveData.observe(this, Observer {
            processResponse(it)
        })
        teamsViewModel.getTeams(competitionID!!)
    }

    private fun processResponse(response: Resource<List<Team>>) {
        when (response.status) {
            Status.SUCCESS -> {
                progressBarLayout.visibility = View.GONE
                if (response.data?.isNullOrEmpty() == false) {
                    adapter.changeList(response.data)
                }
            }
            Status.ERROR -> {
                progressBarLayout.visibility = View.GONE
                Snackbar.make(
                    teamsList,
                    response.message ?: "An error occured",
                    Snackbar.LENGTH_LONG
                ).show()

            }
            Status.LOADING -> {
                if (response.data?.isNullOrEmpty() == false) {
                    adapter.changeList(response.data)
                }
                progressBarLayout.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_teams, container, false)
    }

}
