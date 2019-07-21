package com.android.smartchick.farm

import com.android.smartchick.data.Farm
import com.android.smartchick.data.Member
import com.android.smartchick.util.BasePresenter
import com.android.smartchick.util.BaseView

interface FarmInformationContract {
    interface View : BaseView<Presenter> {
        fun onMemberLoaded(member: Member)
        fun onFarmLoaded( farms: MutableList<Farm>)
        fun onError(error: String? = null)
        fun showLoadingIndicator(active: Boolean)
    }

    interface Presenter : BasePresenter {
        fun loadInformation(memberId: String)
        fun loadFarmInformation(memberId: String)
    }
}