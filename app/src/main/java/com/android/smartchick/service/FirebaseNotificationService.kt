package com.android.smartchick.service

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import android.app.NotificationManager
import android.media.RingtoneManager
import android.app.PendingIntent
import android.content.Intent
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import com.android.smartchick.R


class FirebaseNotificationService: FirebaseMessagingService() {

    companion object {
        const val SENDER_ID = "963836886355"
        const val FCM_KEY = "FCM"
    }

    override fun onMessageReceived(message: RemoteMessage?) {
        super.onMessageReceived(message)
        Log.i(FirebaseNotificationService.javaClass.name, "Firebase Message ${message?.notification!!.body!!}")
        message?.apply {
            sendNotification(message = this.notification!!.body!!)
        }
    }

//    override fun onNewToken(token: String?) {
//
//        Log.d(this.javaClass.name, "Refreshed token: $token")
//        sendRegistrationToServer(token)
//    }

    private fun sendNotification(message: String){

        val intent = Intent(this, FirebaseNotificationService::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_notifications_24dp)
                .setContentTitle("My Firebase Push notification")
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(soundUri)
                .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(0, notificationBuilder.build())

    }
}