package com.android.smartchick.chick.profile

import com.android.smartchick.data.Chicky
import com.android.smartchick.util.BasePresenter
import com.android.smartchick.util.BaseView

interface ProfileChickContract {
    interface View : BaseView<Presenter> {
        fun onFarmIdLoaded( farms: MutableList<String?>)
        fun onChickLoaded( chick: Chicky)
        fun onError(error: String? = null)
        fun showLoadingIndicator(active: Boolean)
    }

    interface Presenter : BasePresenter {
        fun loadChick(chickID: String, farmIdList: MutableList<String?>)
    }
}