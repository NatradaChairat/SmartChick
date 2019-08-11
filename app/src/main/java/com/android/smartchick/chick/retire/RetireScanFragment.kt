package com.android.smartchick.chick.retire

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.smartchick.R
import com.android.smartchick.authentication.login.MEMBER
import com.android.smartchick.authentication.login.MEMBER_ID
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView

class RetireScanFragment :Fragment(), RetireContract.View, ZXingScannerView.ResultHandler {

    override lateinit var presenter: RetireContract.Presenter
    private var mScannerView: ZXingScannerView? = null
    private var memberID: String? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        presenter = RetirePresenter(this)

        var sharedPref: SharedPreferences = activity!!.getSharedPreferences(MEMBER, Context.MODE_PRIVATE)
        memberID = sharedPref.getString(MEMBER_ID, null)

        return inflater.inflate(R.layout.fragment_scan_retire_chick, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onStart() {
        super.onStart()
        mScannerView?.apply { this.startCamera() }
    }

    override fun onPause() {
        super.onPause()
        mScannerView?.apply { this.stopCamera() }
    }

    override fun onResume() {
        super.onResume()
        mScannerView?.apply {
            this.setResultHandler(this@RetireScanFragment)
            this.setAutoFocus(true)
            this.startCamera()
        }

    }

    override fun onLoadedChickIDList(result: MutableList<String>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSuccess() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onError(error: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoadingIndicator(active: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun handleResult(rawResult: Result?) {
        Log.d("QRcode Scanner", "Read: ${rawResult?.text}")
        Toast.makeText(context!!, "${rawResult?.text}", Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun newInstance() = RetireScanFragment()
    }
}