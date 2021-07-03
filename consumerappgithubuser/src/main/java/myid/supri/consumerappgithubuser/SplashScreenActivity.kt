package myid.supri.consumerappgithubuser


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import myid.supri.consumerappgithubuser.databinding.ActivitySplashScreenBinding

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