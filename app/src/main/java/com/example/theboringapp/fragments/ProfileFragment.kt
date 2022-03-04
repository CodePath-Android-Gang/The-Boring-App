package com.example.theboringapp.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import com.example.theboringapp.LoginActivity
import com.example.theboringapp.R
import com.parse.*
import java.io.File

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = ParseUser.getCurrentUser()

        val tvUsername = view.findViewById<TextView>(R.id.tvUsername)
        val ivProfilePic = view.findViewById<ImageView>(R.id.ivProfilePic)
        val tvDescription = view.findViewById<TextView>(R.id.tvDescription)

        Glide.with(this).load(user.getParseFile("profilePic")?.url).into(ivProfilePic)
        tvUsername.text = user.getString("username")
        tvDescription.text = user.getString("description")

        view.findViewById<Button>(R.id.btnLogOut).setOnClickListener{
            ParseUser.logOutInBackground()
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

        view.findViewById<Button>(R.id.addUserBtn).setOnClickListener {
            val friendName = view.findViewById<EditText>(R.id.addUserText).text.toString()
            lateinit var objId : ParseUser
            val CurUser = ParseUser.getCurrentUser()
            val query: ParseQuery<ParseUser> = ParseUser.getQuery()
            query.whereEqualTo("username", friendName)
            query.findInBackground(object: FindCallback<ParseUser> {
                override fun done(users: MutableList<ParseUser>?, e: ParseException?) {
                    if (e != null) {
                        Log.e(FriendFragment.TAG, "Error fetching users")
                    } else {
                        if (users != null) {
                            for (user in users) {
                                Log.i(FriendFragment.TAG, "found user: "+ user.username)
                                objId = user
                                Toast.makeText(requireContext(), "User added to friends list!", Toast.LENGTH_SHORT).show()
                                CurUser.addUnique("friendsList", objId.getString("username"))
                                CurUser.saveInBackground()
                            }
                        }
                    }
                }
            })
        }
    }
}