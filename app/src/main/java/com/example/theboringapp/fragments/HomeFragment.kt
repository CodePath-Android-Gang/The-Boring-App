package com.example.theboringapp.fragments

import android.content.Intent.getIntent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.size
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.example.theboringapp.ActivitiesAdapter
import com.example.theboringapp.BoredActivity
import com.example.theboringapp.R
import okhttp3.Headers

const val TAG = "HomeFragment"
private const val BORED_URL = "https://www.boredapi.com/api/activity/"
class HomeFragment : Fragment() {
    lateinit var rvActivities: RecyclerView
    lateinit var adapter: ActivitiesAdapter
    val activities = ArrayList<BoredActivity>()
    val keys= mutableSetOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // set up recycle view
        rvActivities = view.findViewById(R.id.rvActivities)
        adapter = ActivitiesAdapter(activities)
        rvActivities.layoutManager = LinearLayoutManager(requireContext())
        rvActivities.adapter = adapter

        //val filter = getIntent().getStringExtra("filter")
        val bundle: Bundle? = this.arguments
        val filter: String = bundle?.getString("filter").toString()
        val url: String
        if(filter == "null") {
            url = BORED_URL
        } else {
            url = BORED_URL+filter
        }

        // populate up to 50 activities
        for(i in 1 .. 50)
            addActivity(url)
    }

    fun addActivity(url: String) {
        val client = AsyncHttpClient()
        var activity: BoredActivity

        client.get(url, object: JsonHttpResponseHandler() {
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.e(TAG, "On Failure $statusCode $response")
            }

            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                Log.i(TAG, "On Success: $json")
                if(json.jsonObject.has("error")) {
                    Toast.makeText(context, "No activities found", Toast.LENGTH_SHORT).show()
                    return
                }
                activity = BoredActivity.fromJson(json.jsonObject)
                if(keys.contains(activity.key)) {
                    return
                }

                keys.add(activity.key)
                activities.add(activity)
                adapter.notifyDataSetChanged()
            }
        })
    }
}