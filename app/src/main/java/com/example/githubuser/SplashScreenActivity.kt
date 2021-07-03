package com.example.githubuser

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.core.os.HandlerCompat.postDelayed
import com.example.githubuser.databinding.ActivityFavoriteUserBinding
import com.example.githubuser.databinding.ActivitySplashScreenBinding
import kotlinx.coroutines.delay

class SplashScreenActivity : AppCompatActivity() {
   private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        binding.logoSplashScreen.alpha =0f
        binding.logoSplashScreen.animate().setDuration(1500).alpha(1f).withEndAction{
             val intent = Intent(this,MainActivity::class.java)
           startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            finish()
        }

    }
}