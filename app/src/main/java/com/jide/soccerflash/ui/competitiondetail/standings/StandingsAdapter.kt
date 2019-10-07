package com.jide.soccerflash.ui.competitiondetail.standings

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.PictureDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jide.soccerflash.R
import com.jide.soccerflash.models.TablePositionDetails
import com.jide.soccerflash.utils.GlideApp
import com.jide.soccerflash.utils.SvgSoftwareLayerSetter

class StandingsAdapter : RecyclerView.Adapter<StandingsAdapter.ViewHolder>() {

    var standings = listOf<TablePositionDetails>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.view_standing,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = standings.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(standings.get(position))
    }

    fun changeList(standings: List<TablePositionDetails>) {
        this.standings = standings
        notifyDataSetChanged()
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val position = itemView.findViewById<TextView>(R.id.position)
        val teamLogo = itemView.findViewById<ImageView>(R.id.teamLogo)
        val teamName = itemView.findViewById<TextView>(R.id.teamName)
        val matchesPlayed = itemView.findViewById<TextView>(R.id.matchesPlayed)
        val goalDifference = itemView.findViewById<TextView>(R.id.goalDifference)
        val points = itemView.findViewById<TextView>(R.id.points)
        private val requestBuilder = GlideApp.with(teamLogo)
            .`as`(PictureDrawable::class.java)
            .error(ColorDrawable(Color.DKGRAY))
            .listener(SvgSoftwareLayerSetter())

        fun bind(standing: TablePositionDetails) {
            teamName.text = standing.team?.name
            if (standing.team?.crestUrl?.endsWith("svg", true) == true) {
                requestBuilder.load(standing.team.crestUrl).into(teamLogo)
            } else {
                Glide.with(teamLogo)
                    .load(standing.team?.crestUrl)
                    .into(teamLogo)
            }
            position.text = standing.position.toString()
            matchesPlayed.text = standing.playedGames.toString()
            goalDifference.text = standing.goalDifference.toString()
            points.text = standing.points.toString()
        }
    }
}