package com.yohana.echolearn.notesui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yohana.echolearn.R

class LeaderboardAdapter(
    private val items: List<LeaderboardItem>
) : RecyclerView.Adapter<LeaderboardAdapter.LeaderboardViewHolder>() {

    // ViewHolder to represent each item in the RecyclerView
    class LeaderboardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rankTextView: TextView = itemView.findViewById(R.id.rank)
        val characterImageView: ImageView = itemView.findViewById(R.id.character_image)
        val nameTextView: TextView = itemView.findViewById(R.id.name)
        val pointsTextView: TextView = itemView.findViewById(R.id.points)
    }

    // Inflating the item layout and creating ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaderboardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.leaderboard_item, parent, false)
        return LeaderboardViewHolder(view)
    }

    // Binding data to the ViewHolder
    override fun onBindViewHolder(holder: LeaderboardViewHolder, position: Int) {
        val item = items[position]
        holder.rankTextView.text = (position + 4).toString()
        holder.characterImageView.setImageResource(item.characterImage)
        holder.nameTextView.text = item.name
        holder.pointsTextView.text = "${item.points} Pts"
    }

    // Total count of items in the list
    override fun getItemCount(): Int = items.size
}
