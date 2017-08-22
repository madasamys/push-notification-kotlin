package com.mcruncher.firebase.quickstart.fcm

import android.content.Context
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

/**
 * Author : Madasamy
 * Version : x.x.x
 */

class NotificationAdapter(context: Context, @LayoutRes resource: Int) : ArrayAdapter<Notification>(context, resource)
{

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View?
    {
        var view = convertView
        if (view == null)
        {
            val layoutInflater = LayoutInflater.from(context)
            view = layoutInflater.inflate(R.layout.notification_row, null)
        }
        val notification = getItem(position)
        setTitleTextView(view, notification)
        setType(view, notification)
        setDescription(view, notification)
        return view
    }

    private fun setTitleTextView(view: View?, notification: Notification?)
    {
        val terminalNameTextView = view?.findViewById(R.id.terminalName) as TextView
        terminalNameTextView.text = notification!!.terminalName
    }

    private fun setType(view: View?, notification: Notification?)
    {
        val typeTextView = view?.findViewById(R.id.type) as TextView
        typeTextView.text = notification!!.issueType
    }

    private fun setDescription(view: View?, notification: Notification?)
    {
        val descriptionTextView = view?.findViewById(R.id.description) as TextView
        descriptionTextView.text = notification!!.issueDescription
    }

    fun addObjects(objects: List<Notification>)
    {
        clear()
        addAll(objects)
        notifyDataSetChanged()
    }
}
