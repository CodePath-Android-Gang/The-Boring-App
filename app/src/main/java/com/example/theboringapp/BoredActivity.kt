package com.example.theboringapp

import android.util.Log
import org.json.JSONObject

class BoredActivity {
    var title: String = ""
    var type: String = ""
    var accessibility: Double = 0.0
    var price: Double = 0.0
    var participants: Int = 0
    var link: String = ""
    var key: String = ""

    companion object {
        fun fromJson(jsonObject: JSONObject): BoredActivity {
            val activity = BoredActivity()
            activity.title = jsonObject.getString("activity")
            activity.type = jsonObject.getString("type")
            activity.accessibility = jsonObject.getDouble("accessibility") * 10
            activity.price = jsonObject.getDouble("price") * 10
            activity.participants = jsonObject.getInt("participants")
            activity.link = jsonObject.getString("link")
            activity.key = jsonObject.getString("key")

            return activity
        }
    }


}