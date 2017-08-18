package com.mcruncher.firebase.quickstart.fcm

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import java.lang.reflect.Type
import java.util.ArrayList

/**
 * Author : Madasamy
 * Version : x.x.x
 */

class NotificationService internal constructor(context: Context)
{
    private val sharedPreferences: SharedPreferences

    init
    {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    }

    fun addNotification(notification: Notification)
    {
        val notifications = findAll()
        val gson = Gson()
        notifications.add(notification)
        val json = gson.toJson(notifications)
        sharedPreferences.edit().putString(NOTIFICATION_JSON_KEY, json).apply()
    }


    fun findAll(): MutableList<Notification>
    {
        var notifications: MutableList<Notification> = ArrayList()
        val jsonNotifications = sharedPreferences.getString(NOTIFICATION_JSON_KEY, "")
        val gson = Gson()
        if (!jsonNotifications!!.isEmpty())
        {
            val type = object : TypeToken<List<Notification>>()
            {

            }.type
            notifications = gson.fromJson(jsonNotifications, type)
        }
        return notifications
    }

    companion object
    {
        private val NOTIFICATION_JSON_KEY = "notificationJsonKey"
    }
}
