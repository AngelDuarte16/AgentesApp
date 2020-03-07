package com.angtek.agentesSGM

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.json.JSONObject

class MyFirebaseMessagingService : FirebaseMessagingService(){



    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)

        Log.d("App","Notificacion received")
        Log.d("App","from: ${p0.from}")


        if (p0.data.size >0){
            Log.d("App","message: ${p0.data}")

            try {
                if (p0 != null){
                    val data = p0.data

                       // LogUtility.log("RemoteData : $data")

                        val bodyString = data["message"] ?: ""
                        val body = JSONObject()
                        body.put("Title",data.get("Title"))
                        body.put("IdNotificationSended",data.get("IdNotificationSended"))
                        body.put("Message",data.get("Message"))

                        val idNotificationSended : Int = if (body.has("IdNotificationSended")) (body["IdNotificationSended"] as? Int ?: 0) else 0
                        val title : String = if (body.has("Title")) (body["Title"] as? String ?: "") else ""
                        val message : String = if (body.has("Message")) (body["Message"] as? String ?: "") else ""

                        sendNotifyStandardMessage(idNotificationSended, title, message)


                }
            }catch (exception : Exception){
                Log.d("App","exception ${exception}")
            }



        }
    }



    private fun sendNotifyStandardMessage(IdNotification : Int, Title : String, Message : String) {
        //LogUtility.log("sendNotifyStandardMessage")
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager

        val intent = Intent(this,MainActivity::class.java)

        /*
        if (Usuario.getValue(this, PrefsEnum.token).isEmpty()){
            intent = Intent(this, LoginActivity::class.java)
        }else{
            intent = Intent(this, HomeActivity::class.java)
        }*/

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)

        val defaultSoundUri : Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this)

        notificationBuilder.priority = NotificationCompat.PRIORITY_DEFAULT

        //notificationBuilder.setSmallIcon(R.drawable.icon)
        notificationBuilder.setContentTitle(Title)
        notificationBuilder.setContentText(Message)
        notificationBuilder.setAutoCancel(true)
        notificationBuilder.setSound(defaultSoundUri)
        notificationBuilder.setContentIntent(pendingIntent)

        notificationBuilder.setStyle(NotificationCompat.BigTextStyle().setBigContentTitle(Title).setSummaryText(Message))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notificationBuilder.setSmallIcon(R.drawable.ic_location)
            notificationBuilder.color = resources.getColor(R.color.colorAccent)
        } else {
            notificationBuilder.setSmallIcon(R.drawable.ic_location)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val androidChannel = NotificationChannel("1", "Wop", NotificationManager.IMPORTANCE_HIGH)
            androidChannel.enableLights(true)
            androidChannel.enableVibration(true)
            androidChannel.lightColor = Color.GREEN
            androidChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC

            //val sound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getPackageName() + "/" + R.raw.impressed)
            //val attributes = AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_NOTIFICATION).build()
            //androidChannel.setSound(sound, attributes)
            notificationBuilder.setChannelId("1")
            notificationManager?.createNotificationChannel(androidChannel)
        } else {
            //notificationBuilder.setSound(Uri.parse("android.resource://"+ getPackageName() +"/"+R.raw.impressed))
        }

        notificationManager?.notify(IdNotification, notificationBuilder.build())
    }

}

