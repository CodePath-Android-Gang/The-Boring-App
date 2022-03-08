package com.example.theboringapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.theboringapp.R
import com.example.theboringapp.SpinnerActivity

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

        //Dropdown menu code
        val spinner: Spinner = view.findViewById(R.id.drop_downType)
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            view.context,
            R.array.types_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = SpinnerActivity()
        //Set onClickListener for search submit and random buttons
        view.findViewById<Button>(R.id.submitBtn).setOnClickListener{
            val participants = view.findViewById<EditText>(R.id.etParticipant).text.toString()
            val price = view.findViewById<EditText>(R.id.etPrice).text.toString()
            val type = spinner.selectedItem.toString()
            val accessibility = view.findViewById<EditText>(R.id.etAccessibility).text.toString()



            var hasFilter: Boolean = false
            var filter: String = ""

            if(type != "Any" && type != "") {
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