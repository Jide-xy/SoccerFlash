package com.jide.soccerflash.ui.competitiondetail.standings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.snackbar.Snackbar
import com.jide.soccerflash.R
import com.jide.soccerflash.api.Resource
import com.jide.soccerflash.api.Status
import com.jide.soccerflash.di.Injectable
import com.jide.soccerflash.models.TablePositionDetails
import kotlinx.android.synthetic.main.fragment_standings.*
import kotlinx.android.synthetic.main.layout_empty_layout.*
import javax.inject.Inject

/**
 * A placeholder fragment containing a simple view.
 */
class StandingsFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val standingsViewModel by viewModels<StandingsViewModel> {
        viewModelFactory
    }
    private var competitionID: Int? = null

    private val adapter = StandingsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        competitionID = arguments?.getInt(ARG_COMPETITION_ID)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_standings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tableList.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        tableList.adapter = adapter
        standingsViewModel.standingsLiveData.observe(this, Observer {
            processResponse(it)
        })
        standingsViewModel.getStandings(competitionID!!)
    }

    private fun processResponse(response: Resource<List<TablePositionDetails>>) {
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
                    tableList,
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

    companion object {
        const val ARG_COMPETITION_ID = "ARG_COMPETITION_ID"

        @JvmStatic
        fun newInstance(competitionId: Int): StandingsFragment {
            return StandingsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COMPETITION_ID, competitionId)
                }
            }
        }
    }
}