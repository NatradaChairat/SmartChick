package com.android.smartchick.egg.quality

import com.android.smartchick.data.DailyEgg
import com.android.smartchick.util.BasePresenter
import com.android.smartchick.util.BaseView

interface DailyEggQualityContract {
    interface View : BaseView<Presenter> {
        fun onLastDailyIDLoaded(ID: String)
        fun onSuccess()
        fun onError(error: String? = null)
        fun showLoadingIndicator(active: Boolean)
    }

    interface Presenter : BasePresenter{
        fun getLastDailyEgg(chickID: String)
        fun crateDailyEgg(dailyEgg: DailyEgg)
    }
}