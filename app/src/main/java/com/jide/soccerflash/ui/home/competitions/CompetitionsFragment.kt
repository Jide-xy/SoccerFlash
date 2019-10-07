package com.jide.soccerflash.ui.home.competitions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.jide.soccerflash.R
import com.jide.soccerflash.api.Resource
import com.jide.soccerflash.api.Status
import com.jide.soccerflash.di.Injectable
import com.jide.soccerflash.models.Competition
import com.jide.soccerflash.ui.competitiondetail.standings.StandingsFragment.Companion.ARG_COMPETITION_ID
import kotlinx.android.synthetic.main.fragment_competitions.*
import kotlinx.android.synthetic.main.layout_empty_layout.*
import javax.inject.Inject

class CompetitionsFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val competitionsViewModel by viewModels<CompetitionViewModel> {
        viewModelFactory
    }
    private var adapter = CompetitionsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_competitions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        competitionsList.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        competitionsList.adapter = adapter
        competitionsViewModel.competitionsLiveData.observe(
            this,
            Observer<Resource<List<Competition>>> {
                when (it.status) {
                    Status.SUCCESS -> {
                        progressBarLayout.visibility = View.GONE
                        if (it.data?.isNullOrEmpty() == false) {
                            adapter.changeList(it.data)
                        }
                    }
                    Status.ERROR -> {
                        progressBarLayout.visibility = View.GONE
                        Snackbar.make(
                            competitionsList,
                            it.message ?: "An error occured",
                            Snackbar.LENGTH_LONG
                        ).show()

                    }
                    Status.LOADING -> {
                        if (it.data?.isNullOrEmpty() == false) {
                            adapter.changeList(it.data)
                        }
                        progressBarLayout.visibility = View.VISIBLE
                    }
                }
            })
    }

    inner class CompetitionsAdapter : RecyclerView.Adapter<CompetitionsAdapter.ViewHolder>() {

        var competitions = listOf<Competition>()
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.view_competition_item,
                    parent,
                    false
                )
            )
        }

        override fun getItemCount(): Int = competitions.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(competitions.get(position))
        }

        fun changeList(competitions: List<Competition>) {
            this.competitions = competitions
            notifyDataSetChanged()
        }


        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            init {
                itemView.setOnClickListener {
                    //                    val intent = Intent(activity, CompetitionDetailActivity::class.java)
//                    intent.putExtra(StandingsFragment.ARG_COMPETITION_ID, competitions[adapterPosition].id)
//                    startActivity(intent)
                    findNavController().navigate(
                        R.id.action_navigation_dashboard_to_competitionDetailActivity,
                        bundleOf(
                            ARG_COMPETITION_ID to competitions[adapterPosition].id,
                            "TITLE" to competitions[adapterPosition].name
                        )
                    )
                }
            }

            val name = itemView.findViewById<TextView>(R.id.compName)
            fun bind(competition: Competition) {
                name.text = competition.name
            }
        }
    }
}