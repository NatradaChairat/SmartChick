package com.android.smartchick.chick

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.android.smartchick.R
import com.android.smartchick.chick.profile.ProfileChickFragment

class ChickActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_chick)
        setSupportActionBar(findViewById(R.id.toolbarChick))

        supportFragmentManager.findFragmentById(R.id.contentFrameChick) as ProfileChickFragment?
                ?: ProfileChickFragment.newInstance().also {
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.contentFrameChick, it).commit()
                    }
                }
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, ChickActivity::class.java).apply {

            }
        }
    }
}