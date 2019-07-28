package com.android.smartchick.appointment

import android.util.Log
import com.android.smartchick.common.GeneratorDBKey
import com.android.smartchick.data.Activity
import com.google.firebase.database.FirebaseDatabase

class AppointmentPresenter(val view: AppointmentContract.View) : AppointmentContract.Presenter {

    init {
        view.presenter = this
    }

    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun loadLastActivityId() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addActivity(activity: Activity) {
        view.showLoadingIndicator(true)
        Log.d("Appointment", "${GeneratorDBKey().getKey()}, Value activity: $activity")
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Activity").child(GeneratorDBKey().getKey())
        myRef.setValue(activity)
                .addOnSuccessListener {
                    view.showLoadingIndicator(false)
                    view.onSuccess()
                }
                .addOnFailureListener {
                    view.showLoadingIndicator(true)
                }


    }
}