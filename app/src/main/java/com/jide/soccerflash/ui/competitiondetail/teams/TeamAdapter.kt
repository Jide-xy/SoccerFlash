package com.jide.soccerflash.ui.competitiondetail.teams

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.PictureDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jide.soccerflash.R
import com.jide.soccerflash.models.Team
import com.jide.soccerflash.utils.GlideApp
import com.jide.soccerflash.utils.SvgSoftwareLayerSetter

class TeamAdapter : RecyclerView.Adapter<TeamAdapter.ViewHolder>() {

    var teams = listOf<Team>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.view_teams_list_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = teams.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(teams.get(position))
    }

    fun changeList(teams: List<Team>) {
        this.teams = teams
        notifyDataSetChanged()
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                PlayersBottomSheet.newInstance(
                    teams[adapterPosition].id,
                    teams[adapterPosition].name!!,
                    teams[adapterPosition].crestUrl!!
                )
                    .show((itemView.context as AppCompatActivity).supportFragmentManager, null)
            }
        }

        val logo = itemView.findViewById<ImageView>(R.id.teamLogo)
        val teamName = itemView.findViewById<TextView>(R.id.teamName)
        private val requestBuilder = GlideApp.with(logo)
            .`as`(PictureDrawable::class.java)
            .error(ColorDrawable(Color.DKGRAY))
            .listener(SvgSoftwareLayerSetter())

        fun bind(team: Team) {
            teamName.text = team.name
            if (team.crestUrl?.endsWith("svg", true) == true) {
                requestBuilder.load(team.crestUrl).into(logo)
            } else {
                Glide.with(logo)
                    .load(team.crestUrl)
                    .into(logo)
            }
        }
    }
}