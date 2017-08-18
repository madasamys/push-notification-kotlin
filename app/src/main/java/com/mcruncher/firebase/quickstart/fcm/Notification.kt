package com.mcruncher.firebase.quickstart.fcm

/**
 * Author : Madasamy
 * Version : x.x.x
 */

class Notification
{
    var terminalName: String? = null
    var issue: String? = null
    var issueType: String? = null
    var issueDescription: String? = null
    var createdOn: String? = null

    override fun toString(): String
    {
        return "Notification{" +
                "terminalName='" + terminalName + '\'' +
                ", issue='" + issue + '\'' +
                ", issueType='" + issueType + '\'' +
                ", issueDescription='" + issueDescription + '\'' +
                ", createdOn='" + createdOn + '\'' +
                '}'
    }
}
