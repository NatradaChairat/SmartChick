package com.android.smartchick.authentication.login

import com.android.smartchick.util.BasePresenter
import com.android.smartchick.util.BaseView

interface LoginContract  {

    interface View : BaseView<Presenter> {
        fun onResultLoaded(result: Boolean)
        fun onError(error: String? = null)
    }

    interface Presenter : BasePresenter {
        fun loginByEmailPassword(email: String, password: String)
    }
}