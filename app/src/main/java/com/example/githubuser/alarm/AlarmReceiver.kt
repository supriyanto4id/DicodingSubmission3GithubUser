package com.example.githubuser.alarm

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.os.Message
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.githubuser.MainActivity
import com.example.githubuser.R
import java.lang.reflect.Array

import java.util.*
import kotlin.collections.ArrayList

class AlarmReceiver: BroadcastReceiver()  {

    companion object {
        const val TYPE_DAILY = "Daily Reminder"
        const val EXTRA_MESSAGE = "message"
        const val EXTRA_TYPE = "type"

        private const val ID_DAILY = 100
        //set alarm time
        private const val TIME_DAILY = "09:00"

        private lateinit var calendar :Calendar

    }




    override fun onReceive(context: Context?, intent: Intent) {
        val message = intent.getStringExtra(EXTRA_MESSAGE)
        ShowAlarmNotif(context,message)
    }

    fun setDailyReminderAlarm(context: Context?, type: String , message: String?, time:String?){
        val alarmManager= context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)


        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra(EXTRA_MESSAGE,message)
        intent.putExtra(EXTRA_TYPE,type)
        calendar = Calendar.getInstance()
        if (time==null){
            val itemArray = TIME_DAILY.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(itemArray[0]))
            calendar.set(Calendar.MINUTE,Integer.parseInt(itemArray[1]))
            calendar.set(Calendar.SECOND, 0)
        }else{
            val itemTimeArray = time.split(":").toTypedArray()
            calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(itemTimeArray[0]))
            calendar.set(Calendar.MINUTE,Integer.parseInt(itemTimeArray[1]))
            calendar.set(Calendar.SECOND, 0)
        }


        val pendingInten = PendingIntent.getBroadcast(
                context, ID_DAILY, intent,PendingIntent.FLAG_ONE_SHOT
                )

        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingInten
        )
        Toast.makeText(context,context.getString(R.string.daily_reminder_on), Toast.LENGTH_SHORT).show()

    }

    fun cancelAlaramNotif(context: Context?){
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context,AlarmReceiver::class.java)
        val requestConde = ID_DAILY
        val pendingIntent =PendingIntent.getBroadcast(context,requestConde,intent,0)
            pendingIntent.cancel()
            alarmManager.cancel(pendingIntent)

            Toast.makeText(context,context.getString(R.string.daily_reminder_off), Toast.LENGTH_SHORT).show()
    }
    fun ShowAlarmNotif(context: Context?, message: String?){

        val channelId = "sy4id"
        val channelName = "Daily Notification"
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            context,0,
            intent, PendingIntent.FLAG_ONE_SHOT
        )

        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationManagerCompat = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val builder = NotificationCompat.Builder(context,channelId)
            .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
            .setContentTitle("Daily Reminder")
            .setContentText(message)
            .setAutoCancel(true)
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setSound(alarmSound)

        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            val channel = NotificationChannel(
                    channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            builder.setChannelId(channelId)
            notificationManagerCompat.createNotificationChannel(channel)
        }

        val notification = builder.build()
        notificationManagerCompat.notify(100,notification)


    }
}