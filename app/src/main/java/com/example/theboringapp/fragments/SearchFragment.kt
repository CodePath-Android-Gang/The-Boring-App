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
import java.util.*

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
        view.findViewById<Button>(R.id.submitBtn).setOnClickListener {
            val type = spinner.selectedItem.toString().lowercase(Locale.getDefault())
            val minPart: Int
            val maxPart: Int
            val minPrice: Int
            val maxPrice: Int
            val minAccess: Int
            val maxAccess: Int

            // Get values
            if(view.findViewById<EditText>(R.id.minParticipant).text.isNullOrBlank()) {
                minPart = 1
            } else {
                minPart = view.findViewById<EditText>(R.id.minParticipant).text.toString().toInt()
            }

            if(view.findViewById<EditText>(R.id.maxParticipant).text.isNullOrBlank()) {
                maxPart = 100
            } else {
                maxPart = view.findViewById<EditText>(R.id.maxParticipant).text.toString().toInt()
            }

            if(view.findViewById<EditText>(R.id.minPrice).text.isNullOrBlank()) {
                minPrice = 0
            } else {
                minPrice = view.findViewById<EditText>(R.id.minPrice).text.toString().toInt()
            }

            if(view.findViewById<EditText>(R.id.maxPrice).text.isNullOrBlank()) {
                maxPrice = 100
            } else {
                maxPrice = view.findViewById<EditText>(R.id.maxPrice).text.toString().toInt()
            }

            if(view.findViewById<EditText>(R.id.minAccessibility).text.isNullOrBlank()) {
                minAccess = 0
            } else {
                minAccess = view.findViewById<EditText>(R.id.minAccessibility).text.toString().toInt()
            }

            if(view.findViewById<EditText>(R.id.maxAccessibility).text.isNullOrBlank()) {
                maxAccess = 100
            } else {
                maxAccess = view.findViewById<EditText>(R.id.maxAccessibility).text.toString().toInt()
            }

            Log.i(TAG, "type: $type, minPart: $minPart, maxPart: $maxPart, minPrice: $minPrice, " +
                    "maxPrice: $maxPrice, minAccess: $minAccess, maxAccess: $maxAccess")

            // validate participant range
            if(maxPart < minPart || minPart < 0 || maxPart > 30) {
                Toast.makeText(context, "Invalid participant range", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // validate price range
            if(maxPrice < minPrice || maxPrice > 100) {
                Toast.makeText(context, "Invalid price range", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // validate accessibility range
            if(maxAccess < minAccess || maxPrice > 100) {
                Toast.makeText(context, "Invalid accessibility range", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            // Add filters
            var filter = "?"

            if (type != "any" && type != "") {
                filter += "type=$type"
            }
            filter += "&minparticipants=$minPart"
            filter += "&maxparticipants=$maxPart"
            filter += "&minprice=" + (minPrice/100.0).toString()
            filter += "&maxprice=" + (maxPrice/100.0).toString()
            filter += "&minaccessibility=" + (minAccess/100.0).toString()
            filter += "&maxaccessibility=" + (maxAccess/100.0).toString()

            Log.i("SearchFragment", filter)
            goToMainFragment(filter)
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

    companion object {
        const val TAG = "SearchFragment"
    }
}