package com.example.theboringapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.theboringapp.fragments.FriendFragment
import com.example.theboringapp.fragments.HomeFragment
import com.example.theboringapp.fragments.ProfileFragment
import com.example.theboringapp.fragments.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //A manager for switching between fragments
        val fragmentManager: FragmentManager= supportFragmentManager
        //Set each fragment to this variable once the user clicks
        var fragmentToShow: Fragment? = null
        //Implement individual click action on each item (icons) in bottom nav bar
        findViewById<BottomNavigationView>(R.id.bottom_navigation).setOnItemSelectedListener {
            item->
            when(item.itemId)
            {
                R.id.action_home->{
                    fragmentToShow = HomeFragment()
                }
                R.id.action_search->{
                    fragmentToShow = SearchFragment()
                }
                R.id.action_friends->{
                    fragmentToShow = FriendFragment()
                }
                R.id.action_profile->{
                    fragmentToShow = ProfileFragment()
                }
            }
            //Show the fragment if it's not null
            if (fragmentToShow != null) {
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragmentToShow!!).commit()
            }
            true
        }
        //Show which fragment show first
        findViewById<BottomNavigationView>(R.id.bottom_navigation).selectedItemId = R.id.action_home

    }
}