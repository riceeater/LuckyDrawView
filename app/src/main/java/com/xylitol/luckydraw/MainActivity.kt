package com.xylitol.luckydraw

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    companion object {
        var iconArray = arrayOf(
            R.mipmap.ac0,
            R.mipmap.ac1,
            R.mipmap.ac2,
            R.mipmap.ac3,
            R.mipmap.ac4,
            R.mipmap.ac5,
            R.mipmap.ac6,
            R.mipmap.ac7
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}