package com.example.theboringapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView

class ActivitiesAdapter(val activities: List<BoredActivity>) : RecyclerView.Adapter<ActivitiesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivitiesAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        val view = inflater.inflate(R.layout.activity_post, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ActivitiesAdapter.ViewHolder, position: Int) {
        val activity: BoredActivity = activities[position]

        holder.bind(activity)
    }

    override fun getItemCount(): Int {
        return activities.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvActivity = itemView.findViewById<TextView>(R.id.tvActivity)
        val tvAccessibility = itemView.findViewById<TextView>(R.id.tvAccessibility)
        val tvType = itemView.findViewById<TextView>(R.id.tvType)
        val tvPrice = itemView.findViewById<TextView>(R.id.tvPrice)
        val tvParticipants = itemView.findViewById<TextView>(R.id.tvParticipants)
        val tvLink = itemView.findViewById<TextView>(R.id.tvLink)

        fun bind(activity: BoredActivity) {
            tvActivity.text = activity.title
            tvAccessibility.text = activity.accessibility.toString() + " / 10"
            tvType.text = activity.type
            tvPrice.text = activity.price.toString() + " / 10"
            tvParticipants.text = activity.participants.toString()
            tvLink.text = activity.link
            if(activity.link == "") {
                tvLink.visibility = View.GONE
            } else {
                tvLink.text = activity.link
            }
        }
    }

}