package com.example.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.notification.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private var notificationManager : NotificationManager? = null
    private var CHANNEL_ID = "channel_id"


    private lateinit var countDownTimer: CountDownTimer
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Register channel kedalam sistem
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel(CHANNEL_ID, "Countdown", "Ini merupakan deskripsi")

        binding.btnStart.setOnClickListener{
            countDownTimer.start()
        }

        countDownTimer = object : CountDownTimer(10000, 1000) {
            override fun onTick(p0: Long) {
                //masukin text dari string
                binding.timer.text = getString(R.string.time_reamining, p0/1000)
            }

            override fun onFinish() {
                displayNotification()
            }

        }



    }

    private fun displayNotification(){
        //try yourself code
        val notificationID = 45

        //add yout code
        val intent = Intent(this, MainActivity2::class.java)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)


        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Countdown Timer")
            .setContentText("Your time end")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText(("test")))
            .build()
        notificationManager?.notify(notificationID, notification)


    }

    private fun createNotificationChannel(id: String, name: String, channelDescription: String){
        // validasi notif akan dibuat jika version SDK 26+
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(id, name, importance).apply {
                description = channelDescription
            }
            notificationManager?.createNotificationChannel(channel)
        }
    }
}