package com.mobdeve.s11.salangsang.brian.smartfridge

import android.app.AlarmManager
import android.app.AlertDialog
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.mobdeve.s11.salangsang.brian.smartfridge.databinding.FragmentNotificationBinding
import java.util.Calendar
import java.util.Date


class notificationFragment : Fragment() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val viewBinding = FragmentNotificationBinding.inflate(layoutInflater)

//        createNotificationChannel()
//        viewBinding.submitBtn.setOnClickListener { scheduleNotification() }

        viewBinding.foodBtn.setOnClickListener(View.OnClickListener {
            val intent = Intent (this@notificationFragment.context, NotificationFunction::class.java)
            startActivity(intent)
        })

        // Inflate the layout for this fragment
        return viewBinding.root
    }

//    private fun scheduleNotification() {
//
//        val viewBinding = FragmentNotificationBinding.inflate(layoutInflater)
//
//
//        val intent = Intent (this@notificationFragment.context, foodNotification::class.java)
//        val title = viewBinding.notifTitleET.text.toString()
//        val message = viewBinding.messageET.text.toString()
//        intent.putExtra(titleExtra, title)
//        intent.putExtra(messageExtra, message)
//
//        val pendingIntent = PendingIntent.getBroadcast(
//            requireActivity().application,
//            notificationID,
//            intent,
//            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
//        )
//
//        val alarmManager = requireActivity().application.getSystemService(ALARM_SERVICE) as AlarmManager
//        val time = getTime()
//        alarmManager.setExactAndAllowWhileIdle(
//            AlarmManager.RTC_WAKEUP,
//            time,
//            pendingIntent
//        )
//        showAlert(time, title, message)
//
//    }
//
//    private fun showAlert(time: Long, title: String, message: String) {
//        val date = Date(time)
//        val dateFormat = android.text.format.DateFormat.getLongDateFormat(this@notificationFragment.context)
//        val timeFormat = android.text.format.DateFormat.getTimeFormat(this@notificationFragment.context)
//
//        AlertDialog.Builder(requireActivity())
//            .setTitle("Notification Scheduled!")
//            .setMessage(
//                "Title: " + title +
//                        "\nMessage: " + message +
//                        "\nAt: " + dateFormat.format(date) + " " + timeFormat.format(date))
//            .setPositiveButton("Okay"){_,_ ->}
//            .show()
//    }
//
//    private fun getTime(): Long
//    {
//        val viewBinding = FragmentNotificationBinding.inflate(layoutInflater)
//
//        // remove this if wanna remove clock + sa layout "TimePicker"
//        val minute = viewBinding.timePicker.minute
//        val hour = viewBinding.timePicker.hour
//
//        // calendar
//        val day = viewBinding.datePicker.dayOfMonth
//        val month = viewBinding.datePicker.month
//        val year = viewBinding.datePicker.year
//
//        val calendar = Calendar.getInstance()
//
//        // remove hour & minute for time
//        calendar.set(year, month, day, hour, minute)
//        return calendar.timeInMillis
//    }
//
//
//    @RequiresApi(Build.VERSION_CODES.O)
//    private fun createNotificationChannel() {
//        val name = "Notif Channel"
//        val desc = "Description"
//        val importance = NotificationManager.IMPORTANCE_DEFAULT
//        val channel = NotificationChannel(channelID, name, importance)
//        channel.description = desc
//        val notificationManager = requireActivity().getSystemService(NOTIFICATION_SERVICE) as NotificationManager
//        notificationManager.createNotificationChannel(channel)
//
//    }
//
}