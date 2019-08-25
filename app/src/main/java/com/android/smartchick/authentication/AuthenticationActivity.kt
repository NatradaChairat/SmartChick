package com.android.smartchick.authentication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.smartchick.R

class AuthenticationActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_authentication)

        supportFragmentManager.findFragmentById(R.id.contentFrame) as AuthenticationFragment?
                ?: AuthenticationFragment.newInstance().also {
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.contentFrame, it).commit()
                    }

                }
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, AuthenticationActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
        }
    }
}