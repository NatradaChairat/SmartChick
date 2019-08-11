package com.android.smartchick

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.smartchick.authentication.AuthenticationFragment
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.PermissionNo
import com.yanzhenjie.permission.PermissionYes


class MainActivity : AppCompatActivity() {

    protected val RQ_PERMISSION_BASE: Int = 4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)
        setSupportActionBar(findViewById(R.id.toolbar))

        AndPermission.with(this@MainActivity)
                .requestCode(RQ_PERMISSION_BASE)
                .permission(Manifest.permission.CAMERA)
                .callback(this@MainActivity)
                .start()

    }

    @PermissionYes(4)
    private fun getPermissionYes(grantedPermissions: List<String>) {
        supportFragmentManager.findFragmentById(R.id.contentFrame) as AuthenticationFragment?
                ?: AuthenticationFragment.newInstance().also {
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.contentFrame, it).commit()
                    }

                }
    }

    @PermissionNo(4)
    private fun getPermissionNo(deniedPermissions: List<String>) {
        Toast.makeText(applicationContext, "No Permission ", Toast.LENGTH_SHORT).show()
    }




}
