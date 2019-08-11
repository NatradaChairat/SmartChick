package com.android.smartchick.chick.retire

import com.android.smartchick.data.Chicky

class RetirePresenter(val view: RetireContract.View) : RetireContract.Presenter {
    override fun loadChickIDList() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    init {
        view.presenter = this
    }

    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteChick(chick: Chicky) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}