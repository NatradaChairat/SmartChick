package com.android.smartchick.chick.retire

import com.android.smartchick.data.Chicky
import com.android.smartchick.util.BasePresenter
import com.android.smartchick.util.BaseView

interface RetireContract {
    interface View : BaseView<Presenter> {
        fun onLoadedChickIDList(result: MutableList<String>)
        fun onSuccess()
        fun onError(error: String? = null)
        fun showLoadingIndicator(active: Boolean)
    }

    interface Presenter : BasePresenter {
        fun loadChickIDList()
        fun deleteChick(chick: Chicky)

    }
}