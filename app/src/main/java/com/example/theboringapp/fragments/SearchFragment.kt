package com.example.theboringapp.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.theboringapp.BoredActivity
import com.example.theboringapp.MainActivity
import com.example.theboringapp.R

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
          /*  val participant = view.findViewById<EditText>(R.id.etParticipant)
            val price = view.findViewById<EditText>(R.id.etPrice)
            val type = view.findViewById<EditText>(R.id.etType).text.toString()
            val accessibility = view.findViewById<EditText>(R.id.etAccessibility)

            goToMainActivity()*/
        }
    }
    /*private fun goToMainActivity(){
        val intent = Intent(context, MainActivity::class.java)

        startActivity(intent)
    }*/


}