/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mcruncher.firebase.quickstart.fcm

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.service.notification.StatusBarNotification
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ListView
import android.widget.Toast

import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import org.cryptonode.jncryptor.AES256JNCryptor
import org.cryptonode.jncryptor.JNCryptor


class MainActivity : AppCompatActivity()
{
    private val myBroadcastReceiver = MyBroadcastReceiver()
    private var sharedPreferences: SharedPreferences? = null
    private var notificationAdapter: NotificationAdapter? = null
    private var notificationService: NotificationService? = null


    @RequiresApi(api = Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        notificationService = NotificationService(this)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        //        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        //            // Create channel to show notifications.
        //            String channelId = getString(R.string.default_notification_channel_id);
        //            String channelName = getString(R.string.default_notification_channel_name);
        //            NotificationManager notificationManager = getSystemService(NotificationManager.class);
//                    notificationManager.createNotificationChannel(new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_LOW));
        //        }

        // If a notification message is tapped, any data accompanying the notification
        // message is available in the intent extras. In this sample the launcher
        // intent is fired when the notification is tapped, so any accompanying data would
        // be handled here. If you want a different intent fired, set the click_action
        // field of the notification message to the desired intent. The launcher intent
        // is used when no click_action is specified.
        //
        // Handle possible data accompanying notification message.
        // [START handle_data_extras]

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        var notifications = notificationManager.activeNotifications.size
        Log.d(TAG, "Notifications: $notifications ")
        if (intent.extras != null)
        {
            for (key in intent.extras.keySet())
            {
                val value = intent.extras.get(key)
                Log.d(TAG, "Key: $key Value: $value")
            }
        }
        // [END handle_data_extras]

        //Handle path accompanying scheme
        if (intent.data != null)
        {
            val uri = intent.data
            val path = uri.path
            Log.d(TAG, "Absolute path : $path")
            val substringPath = path.substring(1)
            Log.d(TAG, "Actual path : $substringPath")
            val decoder = Base64.decode(substringPath, Base64.DEFAULT)
            val encrypt = AES256JNCryptor()
            val decryptValue = encrypt.decryptData(decoder, "Secretpassword".toCharArray())
            Log.d(TAG, "Decrypt value :" + String(decryptValue))
            Toast.makeText(this, String(decryptValue), Toast.LENGTH_LONG).show()
//            for (path in uri.pathSegments)
//            {
//                Log.d(TAG, "Path :" + path)
//            }
        }

        val subscribeButton = findViewById(R.id.subscribeButton) as Button
        subscribeButton.setOnClickListener {
            // [START subscribe_topics]'
            FirebaseMessaging.getInstance().subscribeToTopic("news")
            // [END subscribe_topics]
            // Log and toast
            val msg = getString(R.string.msg_subscribed)
            Log.d(TAG, msg)
            Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show()
        }

        val logTokenButton = findViewById(R.id.logTokenButton) as Button
        logTokenButton.setOnClickListener {
            // Get token
            val token = FirebaseInstanceId.getInstance().token

            // Log and toast
            val msg = getString(R.string.msg_token_fmt, token)
            Log.d(TAG, msg)
            Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show()
        }
        val intentFilter = IntentFilter()
        intentFilter.addAction("Received message")
        registerReceiver(myBroadcastReceiver, intentFilter)
        setListView()
    }

    private fun setListView()
    {
        val listView = findViewById(R.id.listview) as ListView
        notificationAdapter = NotificationAdapter(this, R.layout.activity_main)
        listView.adapter = notificationAdapter
        notificationAdapter!!.addObjects(notificationService!!.findAll())
    }

    inner class MyBroadcastReceiver : BroadcastReceiver()
    {

        override fun onReceive(context: Context, intent: Intent)
        {
            notificationService = NotificationService(this@MainActivity)
            Log.d(TAG, "Broad casst receiver")
            Log.d(TAG, "Notification" + notificationService!!.findAll())
            notificationAdapter!!.addObjects(notificationService!!.findAll())
        }
    }

    companion object
    {

        private val TAG = "MainActivity"
    }


}
