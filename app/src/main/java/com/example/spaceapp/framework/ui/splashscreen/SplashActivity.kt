package com.example.spaceapp.framework.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Looper
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.example.spaceapp.databinding.SplashActivityBinding
import com.example.spaceapp.framework.ui.MainActivity
import android.os.Handler

class SplashActivity :  AppCompatActivity()  {
    private lateinit var handler: Handler
    private lateinit var binding: SplashActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SplashActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.splashImageView.animate().rotationBy(360f).setInterpolator(LinearInterpolator()).duration = 3000
        handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 3000)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }
}