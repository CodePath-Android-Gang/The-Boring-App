package com.example.theboringapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.theboringapp.FriendsAdapter
import com.example.theboringapp.R
import com.parse.*

class FriendFragment : Fragment() {

    lateinit var friendsRecyclerView: RecyclerView

    lateinit var adapter: FriendsAdapter

    var friendsList: MutableList<ParseUser> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_friend, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        friendsRecyclerView = view.findViewById(R.id.rvFriends)
        adapter = FriendsAdapter(requireContext(), friendsList)
        friendsRecyclerView.adapter = adapter

        friendsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        val user = ParseUser.getCurrentUser()
        val users = user.getList<String>("friendsList")

        for (user in users.orEmpty())
        {
            Log.i(TAG, "test list")
            if (user != null) {
                queryFriends(user)
            }
        }
    }

    open fun queryFriends(user: String) {
        val query: ParseQuery<ParseUser> = ParseUser.getQuery()
        query.whereEqualTo("username", user)
        query.findInBackground(object: FindCallback<ParseUser> {
            override fun done(users: MutableList<ParseUser>?, e: ParseException?) {
                if (e != null) {
                    Log.e(TAG, "Error fetching users")
                } else {
                    if (users != null) {
                        for (user in users) {
                            Log.i(TAG, "user: "+ user.username)
                        }

                        friendsList.addAll(users)
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        })
    }

    companion object {
        const val TAG = "test"
    }
}

