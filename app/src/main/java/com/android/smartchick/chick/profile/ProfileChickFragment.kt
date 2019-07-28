package com.android.smartchick.chick.profile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.smartchick.R
import kotlinx.android.synthetic.main.fragment_scan_profile_chick.*
import android.util.Log
import android.widget.Toast
import com.android.smartchick.authentication.login.MEMBER
import com.android.smartchick.authentication.login.MEMBER_ID
import com.android.smartchick.data.Chicky
import me.dm7.barcodescanner.zxing.ZXingScannerView
import com.google.zxing.Result


class ProfileChickFragment : Fragment(), ProfileChickContract.View, ZXingScannerView.ResultHandler  {

    override lateinit var presenter: ProfileChickContract.Presenter
    private var mScannerView: ZXingScannerView? = null
    private var memberID: String? = null
    private var farmIDList = mutableListOf<String?>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var sharedPref: SharedPreferences = activity!!.getSharedPreferences(MEMBER, Context.MODE_PRIVATE)
        memberID = sharedPref.getString(MEMBER_ID, null)
        return inflater.inflate(R.layout.fragment_scan_profile_chick, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = ProfileChickPresenter(this, memberID!!)
        presenter.start()

        mScannerView = ZXingScannerView(activity)
        mScannerView!!.setAutoFocus(true)
        mScannerView!!.setResultHandler(this)
        frame_layout_camera.addView(mScannerView)
        initView()
        initListener()
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
            this.setResultHandler(this@ProfileChickFragment)
            this.setAutoFocus(true)
            this.startCamera()
        }

    }

    override fun handleResult(rawResult: Result?) {
        Log.d("QRcode Scanner", "Read: ${rawResult?.text}")
        Toast.makeText(context!!, "${rawResult?.text}", Toast.LENGTH_SHORT).show()
        tvProfileChickID.text = rawResult?.text
    }

    override fun onFarmIdLoaded(farms: MutableList<String?>) {
        farmIDList = farms
    }

    override fun onChickLoaded(chick: Chicky) {
        Log.d("Profile", "onLoaded chicky : $chick")

        var profileInfoChickFragment = ProfileInfoChickFragment.newInstance()
        var bundle = Bundle()
        bundle.putString("CHICKID", chick.id_chick)
        bundle.putString("FARMID", chick.id_farm)
        bundle.putString("VACCINE", chick.vaccine)
        bundle.putInt("WEIGHT", chick.weight!!)
        bundle.putString("BOD", chick.birthdate)

        profileInfoChickFragment.arguments = bundle

        fragmentManager!!.beginTransaction().replace(R.id.contentFrame, profileInfoChickFragment).addToBackStack(null).commit()

    }

    override fun onError(error: String?) {
        Toast.makeText(context, "$error", Toast.LENGTH_SHORT).show()
    }

    override fun showLoadingIndicator(active: Boolean) {
        when (active) {
            true -> {
                llScanProfile.visibility = View.GONE
                progressBarScanProfile.visibility = View.VISIBLE
            }
            false -> {
                llScanProfile.visibility = View.VISIBLE
                progressBarScanProfile.visibility = View.GONE
            }
        }
    }

    private fun initView() {

        mScannerView = ZXingScannerView(activity)
        mScannerView!!.setAutoFocus(true)
        mScannerView!!.setResultHandler(this)
        frame_layout_camera.addView(mScannerView)

        frame_layout_camera.setOnClickListener {
            mScannerView!!.resumeCameraPreview(this)
        }

    }

    private fun initListener(){
        etProfileChickID.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) { }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                tvProfileChickID.text = s.toString()
            }
        })

        btnSubmitProfileID.setOnClickListener {
            presenter.loadChick(chickID = tvProfileChickID.text.toString(), farmIdList = farmIDList)

        }

    }

    companion object {
        fun newInstance() = ProfileChickFragment()
    }
}