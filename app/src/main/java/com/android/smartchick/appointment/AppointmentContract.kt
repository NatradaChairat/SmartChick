package com.android.smartchick.appointment

import com.android.smartchick.data.Activity
import com.android.smartchick.util.BasePresenter
import com.android.smartchick.util.BaseView

interface AppointmentContract {
    interface View : BaseView<Presenter> {
        fun onSuccess()
        fun onError(error: String? = null)
        fun showLoadingIndicator(active: Boolean)
    }

    interface Presenter : BasePresenter {
        fun loadLastActivityId()
        fun addActivity(activity: Activity)

    }
}