package com.example.githubuser

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.ScriptGroup
import android.view.MenuItem
import android.view.View
import com.example.githubuser.alarm.AlarmReceiver
import com.example.githubuser.alarm.TimePickerFragment
import com.example.githubuser.databinding.ActivityNotificationBinding
import java.text.SimpleDateFormat
import java.util.*

class NotificationActivity : AppCompatActivity() {
    companion object {
        const val PREFS_NAME = "SettingPref"
        private const val DAILY = "daily"
        private const val TIME_PICKER_ONCE_TAG = "TimePickerOnce"
        private const val TIME_PICKER_REPEAT_TAG = "TimePickerRepeat"

    }

    private lateinit var alarmReceiver: AlarmReceiver
    private lateinit var mSharedPreferences: SharedPreferences

    lateinit var binding: ActivityNotificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title ="Setting"

        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        alarmReceiver = AlarmReceiver()
        mSharedPreferences =getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)


        setSwitch()
        binding.switchDailyReminder.setOnCheckedChangeListener{_,isCheked->
           val onceTime = null //binding?.tvSetTime.text.toString()
            if (isCheked){
                alarmReceiver.setDailyReminderAlarm(this,AlarmReceiver.TYPE_DAILY,"find new user in github user",onceTime)

            }else{
                alarmReceiver.cancelAlaramNotif(this)
            }
            saveChengeSwitch(isCheked)
        }

       // binding.setTime.setOnClickListener(this)

    }

    private fun setSwitch(){


        binding.switchDailyReminder.isChecked =mSharedPreferences.getBoolean(DAILY,false)
    }

    private fun saveChengeSwitch(status :Boolean){
        val edit = mSharedPreferences.edit()
            edit.putBoolean(DAILY,status)
            edit.apply()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId==   android.R.id.home)
            finish()
        return super.onOptionsItemSelected(item)
    }

//    override fun onClick(v: View?) {
//        when(v?.id){
//            R.id.set_time->{
//                val timePickerFragmentOne = TimePickerFragment()
//                timePickerFragmentOne.show(supportFragmentManager, TIME_PICKER_ONCE_TAG)
//            }
//        }
//    }
//    override fun onDialogTimeSet(tag: String?, hourOfDay: Int, minute: Int) {
//
//        // Siapkan time formatter-nya terlebih dahulu
//        val calendar = Calendar.getInstance()
//        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
//        calendar.set(Calendar.MINUTE, minute)
//
//        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
//
//        // Set text dari textview berdasarkan tag
//        when (tag) {
//            TIME_PICKER_ONCE_TAG -> binding?.tvSetTime?.text = dateFormat.format(calendar.time)
//            TIME_PICKER_REPEAT_TAG -> {}
//            else -> {
//            }
//        }
//    }

}