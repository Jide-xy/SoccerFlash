package com.jide.soccerflash.ui.competitiondetail.teams

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.PictureDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.jide.soccerflash.R
import com.jide.soccerflash.api.Resource
import com.jide.soccerflash.api.Status
import com.jide.soccerflash.di.Injectable
import com.jide.soccerflash.models.TeamPlayer
import com.jide.soccerflash.utils.GlideApp
import com.jide.soccerflash.utils.SvgSoftwareLayerSetter
import kotlinx.android.synthetic.main.dialog_team_player.*
import kotlinx.android.synthetic.main.layout_empty_layout.*
import javax.inject.Inject

const val ARG_TEAM_ID: String = "ARG_TEAM_ID"
const val ARG_TEAM_NAME: String = "ARG_TEAM_NAME"
const val ARG_TEAM_LOGO_URL: String = "ARG_TEAM_LOGO_URL"

class PlayersBottomSheet : BottomSheetDialogFragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val teamsViewModel by viewModels<TeamsViewModel> {
        viewModelFactory
    }

    private var teamId: Int? = null
    private lateinit var teamName: String
    private lateinit var teamLogoUrl: String
    private val adapter = PlayerAdapter()

    companion object {

        @JvmStatic
        fun newInstance(id: Int, name: String, url: String): PlayersBottomSheet {
            return PlayersBottomSheet().apply {
                arguments = Bundle().apply {
                    putInt(ARG_TEAM_ID, id)
                    putString(ARG_TEAM_NAME, name)
                    putString(ARG_TEAM_LOGO_URL, url)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        teamId = arguments?.getInt(ARG_TEAM_ID)
        teamName = arguments?.getString(ARG_TEAM_NAME) ?: ""
        teamLogoUrl = arguments?.getString(ARG_TEAM_LOGO_URL) ?: ""
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_team_player, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val requestBuilder = GlideApp.with(this)
            .`as`(PictureDrawable::class.java)
            .error(ColorDrawable(Color.GRAY))
            .listener(SvgSoftwareLayerSetter())
        if (teamLogoUrl.endsWith("svg", true)) {
            requestBuilder.load(teamLogoUrl).into(teamLogo)
        } else {
            Glide.with(teamLogo)
                .load(teamLogoUrl)
                .into(teamLogo)
        }
        playerList.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        playerList.adapter = adapter
        teamsViewModel.teamPlayerLiveData.observe(this, Observer {
            processResponse(it)
        })
        teamsViewModel.getPlayers(teamId!!)
        team_name.text = teamName
        close.setOnClickListener { dismiss() }
    }

    private fun processResponse(response: Resource<List<TeamPlayer>>) {
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
                    playerList,
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

    inner class PlayerAdapter : RecyclerView.Adapter<PlayerAdapter.ViewHolder>() {

        var players = listOf<TeamPlayer>()
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.view_player_list_item,
                    parent,
                    false
                )
            )
        }

        override fun getItemCount(): Int = players.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(players.get(position))
        }

        fun changeList(players: List<TeamPlayer>) {
            this.players = players
            notifyDataSetChanged()
        }


        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            val playerNumber = itemView.findViewById<TextView>(R.id.playerNumber)
            val playerName = itemView.findViewById<TextView>(R.id.playerName)
            val playerPosition = itemView.findViewById<TextView>(R.id.playerPosition)


            fun bind(player: TeamPlayer) {
                playerNumber.text = player.shirtNumber?.toString() ?: "N/A"
                playerName.text = player.name
                playerPosition.text = player.position
            }
        }
    }
}