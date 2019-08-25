package com.android.smartchick.authentication

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.android.smartchick.R
import com.android.smartchick.authentication.login.LoginFragment
import com.android.smartchick.authentication.login.LoginPresenter
import com.android.smartchick.authentication.register.RegisterFragment
import com.android.smartchick.authentication.register.RegisterPresenter
import kotlinx.android.synthetic.main.fragment_authentication.*

class AuthenticationFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_authentication, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
    }

    private fun initListener(){
        btnRegister.setOnClickListener {

//            (activity as AppCompatActivity).supportFragmentManager.findFragmentById(R.id.contentFrame) as RegisterFragment?
//                    ?: RegisterFragment.newInstance().also {
//                        (activity as AppCompatActivity).supportFragmentManager.beginTransaction().apply {
//                            replace(R.id.contentFrame, it).commit()
//                        }
//                        RegisterPresenter()
//                    }
            Toast.makeText(context!!, "Application not support Register function", Toast.LENGTH_SHORT).show()

        }

        btnLogin.setOnClickListener {

            (activity as AppCompatActivity).supportFragmentManager.beginTransaction().apply {
                replace(R.id.contentFrame, LoginFragment.newInstance()).commit()
            }
        }
    }

    companion object {
        fun newInstance() = AuthenticationFragment()
    }
}