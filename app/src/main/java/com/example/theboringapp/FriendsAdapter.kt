package com.example.theboringapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.parse.ParseUser

class FriendsAdapter(val context: Context, val friends: List<ParseUser>) : RecyclerView.Adapter<FriendsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.activity_friend, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: FriendsAdapter.ViewHolder, position: Int) {
        val friend = friends.get(position)
        holder.bind(friend)
    }

    override fun getItemCount(): Int {
        return friends.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivFriendsPic: ImageView
        val tvFriendsName : TextView

        init {
            tvFriendsName = itemView.findViewById(R.id.tvFriendName)
            ivFriendsPic = itemView.findViewById(R.id.ivFriendPic)
        }

        fun bind(user: ParseUser) {
            tvFriendsName.text = user.getString("username")
            Glide.with(itemView.context).load(user.getParseFile("profilePic")?.url).into(ivFriendsPic)
        }
    }
}