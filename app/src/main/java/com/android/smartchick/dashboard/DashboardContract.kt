package com.android.smartchick.dashboard

import com.android.smartchick.data.Member
import com.android.smartchick.util.BasePresenter
import com.android.smartchick.util.BaseView

interface DashboardContract {
    interface View : BaseView<Presenter> {
        fun onResultLoaded(member: Member)
        fun onError(error: String? = null)
    }

    interface Presenter : BasePresenter {
        fun loadInformation(memberId: String)
    }
}