package com.android.smartchick.chick.add

import com.android.smartchick.data.Chicky
import com.android.smartchick.util.BasePresenter
import com.android.smartchick.util.BaseView

interface AddChickContract {
    interface View : BaseView<Presenter> {
        fun onSuccess()
        fun onError(error: String? = null)
        fun showLoadingIndicator(active: Boolean)
    }

    interface Presenter : BasePresenter {
        fun loadChickIDList()
        fun addChick(chick: Chicky)
    }
}