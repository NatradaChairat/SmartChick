package com.android.smartchick.chick.add

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.smartchick.R
import com.google.zxing.Result
import kotlinx.android.synthetic.main.fragment_scan_add_chick.*
import kotlinx.android.synthetic.main.fragment_scan_add_chick.frame_layout_camera
import kotlinx.android.synthetic.main.fragment_scan_daily_egg.*
import me.dm7.barcodescanner.zxing.ZXingScannerView

class AddChickFragment : Fragment(), AddChickContract.View, ZXingScannerView.ResultHandler  {

    override lateinit var presenter: AddChickContract.Presenter


    private var mScannerView: ZXingScannerView? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_scan_add_chick, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = AddChickPresenter(this)

        initView()
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
            this.setResultHandler(this@AddChickFragment)
            this.setAutoFocus(true)
            this.startCamera()
        }

    }

    override fun handleResult(rawResult: Result?) {
        Log.d("QRcode Scanner", "Read: ${rawResult?.text}")
        Toast.makeText(context!!, "${rawResult?.text}", Toast.LENGTH_SHORT).show()
        tvChickID.text = rawResult?.text
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

    private fun initView() {

        mScannerView = ZXingScannerView(activity)
        mScannerView!!.setAutoFocus(true)
        mScannerView!!.setResultHandler(this)
        frame_layout_camera.addView(mScannerView)

        frame_layout_camera.setOnClickListener {

            mScannerView!!.resumeCameraPreview(this)
        }
    }

    private fun initListener() {
        etAddChickID.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                tvChickID.text = s.toString()
            }
        })

        nextAddChick.setOnClickListener {
//            Log.d("AddChick", "${checkChickID(tvChickID.text.toString())}")
//            when (checkChickID(tvChickID.text.toString())) {
//                true -> {
//                    var dailyEggQualityFragment = DailyEggQualityFragment.newInstance()
//                    Log.d("DailyScan", "chicky ID: ${tvChickID.text}")
//                    var bundle = Bundle()
//                    bundle.putString("CHICKID", tvChickID.text.toString())
//                    bundle.putString("DATE", date)
//                    dailyEggQualityFragment.arguments = bundle
//
//                    fragmentManager!!.beginTransaction().replace(R.id.contentFrame, dailyEggQualityFragment).addToBackStack(null).commit()
//
//                }
//                false -> {
//                    Toast.makeText(context!!, "Incorrect Chick ID", Toast.LENGTH_LONG).show()
//                }
//            }
        }
    }

    private fun checkChickIDIsDuplicate(chickID: String): Boolean {
        Log.d("DailyScan", "checkChickID $chickID")
//        return when (chickID in chickIDList) {
//            true -> {
//                true
//            }
//            false -> {
//                false
//            }
//        }
        return true
    }

    companion object {
        fun newInstance() = AddChickFragment()
    }
}