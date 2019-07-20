package com.android.smartchick.authentication.login

import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlin.contracts.contract

class LoginPresenter(val view: LoginContract.View) : LoginContract.Presenter {

    private val TAG = "LoginPresenter"
    private var auth = FirebaseAuth.getInstance()

    init {
        view.presenter = this
    }

    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loginByEmailPassword(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success ${auth.currentUser!!.uid}")
                        val user = auth.currentUser
                        view.onResultLoaded(true, user!!.uid)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        view.onResultLoaded(false, null)
                    }

                }
    }

}