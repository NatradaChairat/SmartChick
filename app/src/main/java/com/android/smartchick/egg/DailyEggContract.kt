package com.android.smartchick.egg

import com.android.smartchick.data.Chicky
import com.android.smartchick.data.DailyEgg
import com.android.smartchick.data.Farm
import com.android.smartchick.data.Member
import com.android.smartchick.util.BasePresenter
import com.android.smartchick.util.BaseView

interface DailyEggContract {
    interface View : BaseView<Presenter> {
        fun onFarmLoaded(result: MutableList<Farm>)
        fun onChickyLoaded(result: MutableList<Chicky>)
        fun onError(error: String? = null)
        fun showLoadingIndicator(active: Boolean)
    }

    interface Presenter : BasePresenter {
        fun loadChickyInformation(farmID: String)
        fun loadFarmInformation(memberID: String)
    }
}