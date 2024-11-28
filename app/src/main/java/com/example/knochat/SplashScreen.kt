package com.example.knochat

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import de.hdodenhof.circleimageview.CircleImageView

class SplashScreen : AppCompatActivity() {
    lateinit var topAnimation:Animation
    lateinit var bottomAnimation:Animation
    val logoName :TextView
        get() = findViewById(R.id.logoName)
    val logo : ImageView
        get()= findViewById(R.id.logoImage)
    val slogan :TextView
        get()= findViewById(R.id.slogan)
    val byLogoImage :ImageView
        get()= findViewById(R.id.byLogoImage)
    val byText :TextView
        get()= findViewById(R.id.byText)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash_screen)
        topAnimation=AnimationUtils.loadAnimation(this,R.anim.top_animation)
        bottomAnimation=AnimationUtils.loadAnimation(this,R.anim.bottom_animation)
        logoName.animation=topAnimation
        logo.animation=bottomAnimation
        slogan.animation=topAnimation
        byText.animation=bottomAnimation
        byLogoImage.animation=bottomAnimation

        Handler(Looper.getMainLooper()).postDelayed({startActivity(Intent(this,LoginActivity::class.java)) },3000)


    }
}