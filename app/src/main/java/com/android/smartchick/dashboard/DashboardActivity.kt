package com.android.smartchick.dashboard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.android.smartchick.R

class DashboardActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_dashboard)

        val hasMemberID = intent.hasExtra("MEMBER_ID")

        when (hasMemberID) {
            true -> {
                val memberID = intent.extras.get("MEMBER_ID") as String
                supportFragmentManager.findFragmentById(R.id.contentFrameDashBoard) as DashboardFragment?
                        ?: DashboardFragment.newInstance(memberID).also {
                            supportFragmentManager.beginTransaction().apply {
                                replace(R.id.contentFrameDashBoard, it).commit()
                            }
                        }
            }
            false -> finish()
        }
    }

    companion object {
        fun newIntent(context: Context, memberID: String): Intent {
            return Intent(context, DashboardActivity::class.java).apply {
                putExtra("MEMBER_ID", memberID)
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
        }
    }
}