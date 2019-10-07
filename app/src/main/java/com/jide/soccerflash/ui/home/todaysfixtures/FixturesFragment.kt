package com.jide.soccerflash.ui.home.todaysfixtures

import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.RecyclerView
import com.jide.soccerflash.R
import com.jide.soccerflash.api.Resource
import com.jide.soccerflash.api.Status
import com.jide.soccerflash.di.Injectable
import com.jide.soccerflash.models.MatchDetails
import com.jide.soccerflash.ui.competitiondetail.standings.StandingsFragment
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.layout_empty_layout.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

const val DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssX"

class FixturesFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val homeViewModel by viewModels<FixturesViewModel> {
        viewModelFactory
    }
    private var adapter = FixtureAdapter()
    private var competitionID: Int? = null

    companion object {
        @JvmStatic
        fun newInstance(competitionId: Int): FixturesFragment {
            return FixturesFragment().apply {
                arguments = Bundle().apply {
                    putInt(StandingsFragment.ARG_COMPETITION_ID, competitionId)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fixtureList.addItemDecoration(DividerItemDecoration(context, VERTICAL))
        fixtureList.adapter = adapter
        homeViewModel.todaysMatchesLiveData.observe(this, Observer<Resource<List<MatchDetails>>> {
            processResponse(it)
        })
        homeViewModel.matchesLiveData.observe(this, Observer<Resource<List<MatchDetails>>> {
            processResponse(it)
        })
        getFixtures()
        errorButton.setOnClickListener {
            getFixtures()
        }
    }

    private fun getFixtures() =
        if (competitionID == null || competitionID == -1) homeViewModel.getTodaysMatches() else homeViewModel.getMatchesForCompetition(
            competitionID!!
        )

    private fun processResponse(response: Resource<List<MatchDetails>>) {
        when (response.status) {
            Status.SUCCESS -> {
                progressBarLayout.visibility = View.GONE
                if (response.data?.isNullOrEmpty() == false) {
                    adapter.changeList(response.data)
                }
                if (adapter.itemCount == 0) {
                    errorLayout.visibility = View.VISIBLE
                    fixtureList.visibility = View.GONE
                }
            }
            Status.ERROR -> {
                progressBarLayout.visibility = View.GONE
                Toast.makeText(context, response.message ?: "An error occured", Toast.LENGTH_LONG)
                    .show()
                if (adapter.itemCount == 0) {
                    errorLayout.visibility = View.VISIBLE
                    fixtureList.visibility = View.GONE
                }
            }
            Status.LOADING -> {
                if (response.data?.isNullOrEmpty() == false) {
                    adapter.changeList(response.data)
                }
                progressBarLayout.visibility = View.VISIBLE
                errorLayout.visibility = View.GONE
                fixtureList.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        competitionID = arguments?.getInt(StandingsFragment.ARG_COMPETITION_ID, -1)
    }

    inner class FixtureAdapter : RecyclerView.Adapter<FixtureAdapter.ViewHolder>() {

        var fixtures = listOf<MatchDetails>()
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.view_todays_fixture_item,
                    parent,
                    false
                )
            )
        }

        override fun getItemCount(): Int = fixtures.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(fixtures.get(position))
        }

        fun changeList(fixtures: List<MatchDetails>) {
            this.fixtures = fixtures
            notifyDataSetChanged()
        }


        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val match_state = itemView.findViewById<TextView>(R.id.match_state)
            val time = itemView.findViewById<TextView>(R.id.time)
            val md = itemView.findViewById<TextView>(R.id.md)
            val team1 = itemView.findViewById<TextView>(R.id.team1)
            val team2 = itemView.findViewById<TextView>(R.id.team2)
            val team1Score = itemView.findViewById<TextView>(R.id.team1Score)
            val team2Score = itemView.findViewById<TextView>(R.id.team2Score)
            val countDownTimer = itemView.findViewById<TextView>(R.id.countDownTimer)

            fun bind(match: MatchDetails) {
                match_state.text = match.status
                time.text = DateFormat.format(
                    "HH:mm",
                    SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).parse(match.utcDate!!)
                )
                md.text = "MD: ${match.matchday}"
                team1.text = match.homeTeam?.name
                team2.text = match.awayTeam?.name
                team1Score.text = match.score?.fullTime?.homeTeam?.toString() ?: "0"
                team2Score.text = match.score?.fullTime?.awayTeam?.toString() ?: "0"
                countDownTimer.text = match.minute ?: "00"
            }
        }
    }
}