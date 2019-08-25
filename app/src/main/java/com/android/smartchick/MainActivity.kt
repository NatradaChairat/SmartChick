package com.android.smartchick

import android.Manifest.permission.CAMERA
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.firebase.auth.FirebaseAuth
import com.android.smartchick.authentication.AuthenticationActivity
import com.android.smartchick.dashboard.DashboardActivity


class MainActivity : AppCompatActivity() {

    private val RQ_PERMISSION_BASE: Int = 4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_authentication)

        initAskPermission()

    }

    private fun initAskPermission() {

        if (!hasPermissions(this, arrayOf(CAMERA))) {
            ActivityCompat.requestPermissions(this, arrayOf(CAMERA), RQ_PERMISSION_BASE)
        } else {
            var auth = FirebaseAuth.getInstance()

            if (auth.currentUser != null) {
                applicationContext?.apply {
                    startActivity(DashboardActivity.newIntent(this, auth.currentUser!!.uid))
                }
                finish()
            }else {
                startAuthenticationActivity()
            }

        }
    }

    private fun hasPermissions(context: Context, permissions: Array<String>): Boolean {
        for (permission in permissions) {
            if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    private fun startAuthenticationActivity() {
        startActivity(AuthenticationActivity.newIntent(context = applicationContext))
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            RQ_PERMISSION_BASE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startAuthenticationActivity()
                } else {
                    Toast.makeText(applicationContext, "No Permission", Toast.LENGTH_SHORT).show()                }
            }
        }
    }


}
