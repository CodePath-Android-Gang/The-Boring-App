package com.example.theboringapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.theboringapp.R
import java.io.File
import com.parse.ParseFile
import com.parse.ParseUser

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

        val user = ParseUser()

        val tvUsername = view.findViewById<TextView>(R.id.tvUsername)
        val ivProfilePic = view.findViewById<ImageView>(R.id.ivProfilePic)
        val tvDescription = view.findViewById<TextView>(R.id.tvDescription)

        Glide.with(this).load(user.getParseFile("username")?.url).into(ivProfilePic)
        tvUsername.text = user.getString("username")
        tvDescription.text = user.getString("description")
    }
}