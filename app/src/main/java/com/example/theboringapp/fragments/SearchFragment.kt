package com.example.theboringapp.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.theboringapp.BoredActivity
import com.example.theboringapp.MainActivity
import com.example.theboringapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class SearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Set onClickListener for search submit and random buttons
        view.findViewById<Button>(R.id.submitBtn).setOnClickListener{
            val participants = view.findViewById<EditText>(R.id.etParticipant).text.toString()
            val price = view.findViewById<EditText>(R.id.etPrice).text.toString()
            val type = view.findViewById<EditText>(R.id.etType).text.toString()
            val accessibility = view.findViewById<EditText>(R.id.etAccessibility).text.toString()

            var hasFilter: Boolean = false
            var filter: String = ""

            if(type != "") {
                hasFilter = true
                filter = "?"
                filter += "type=$type"
            }

            if(participants != "") {
                if(!hasFilter) {
                    hasFilter = true
                    filter = "?"
                } else {
                    filter += "&"
                }
                filter += "participants=$participants"
            }

            if(price != "") {
                if(!hasFilter) {
                    hasFilter = true
                    filter = "?"
                } else {
                    filter += "&"
                }
                filter += "price=$price"
            }

            if(accessibility != "") {
                if(!hasFilter) {
                    hasFilter = true
                    filter = "?"
                } else {
                    filter += "&"
                }
                filter += "accessibility=$accessibility"
            }

            Log.i("SearchFragment", filter)
            goToMainFragment(filter)
        }

        view.findViewById<Button>(R.id.randomBtn).setOnClickListener{
            goToMainFragment("")
        }
    }
    private fun goToMainFragment(filter: String){
        val fragment = HomeFragment()
        val fragmentTransaction = requireFragmentManager().beginTransaction()
        val bundle = Bundle()
        bundle.putString("filter", filter)
        fragment.arguments = bundle
        fragmentTransaction.replace(R.id.flContainer, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }


}