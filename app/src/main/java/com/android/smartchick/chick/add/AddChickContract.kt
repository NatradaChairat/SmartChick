package com.android.smartchick.chick.add

import com.android.smartchick.data.Chicky
import com.android.smartchick.data.Farm
import com.android.smartchick.util.BasePresenter
import com.android.smartchick.util.BaseView

interface AddChickContract {
    interface View : BaseView<Presenter> {
        fun onSuccess()
        fun onChickIDListLoaded(result: MutableList<String?>)
        fun onFarmLoaded(result: MutableList<Farm>)
        fun onError(error: String? = null)
        fun showLoadingIndicator(active: Boolean)
    }

    interface Presenter : BasePresenter {
        fun loadChickIDList(memberID: String)
        fun loadFarmList(memberID: String)
        fun addChick(chick: Chicky)
    }
}