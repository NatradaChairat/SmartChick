package com.android.smartchick.authentication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.android.smartchick.R

class AuthenticationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_authentication)
        setSupportActionBar(findViewById(R.id.toolbar))

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

            }
        }
    }
}