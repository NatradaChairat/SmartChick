package com.android.smartchick

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.smartchick.authentication.AuthenticationActivity
import android.content.Intent
import android.os.Handler
import androidx.core.os.HandlerCompat.postDelayed
import com.android.smartchick.authentication.AuthenticationFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)
        setSupportActionBar(findViewById(R.id.toolbar))

        supportFragmentManager.findFragmentById(R.id.contentFrame) as AuthenticationFragment?
                ?: AuthenticationFragment.newInstance().also {
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.contentFrame, it).commit()
                    }
                }


    }


}
