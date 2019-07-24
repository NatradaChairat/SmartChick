package com.android.smartchick.egg.scan

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
import com.android.smartchick.egg.quality.DailyEggQualityFragment
import com.google.zxing.Result
import kotlinx.android.synthetic.main.fragment_daily_egg.*
import kotlinx.android.synthetic.main.fragment_scan_daily_egg.*
import me.dm7.barcodescanner.zxing.ZXingScannerView
import java.util.*

class DailyEggScanFragment : Fragment(), ZXingScannerView.ResultHandler {

    private var chickIDList: MutableList<String> = mutableListOf()
    private var date :String? = null
    private var mScannerView: ZXingScannerView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_scan_daily_egg, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.arguments?.apply {
            Log.d("DailyScan", "Argument chicky ")
            chickIDList = this.getStringArray("CHICKID_ARRAY").toMutableList()
            date = this.getString("DATE")
        }
        Log.d("DailyScan", "Date $date, $chickIDList")
        initScanView()
        initListener()

    }

    override fun handleResult(rawResult: Result?) {
        Log.d("QRcode Scanner", "Read: ${rawResult?.text}")
        Toast.makeText(context!!, "${rawResult?.text}", Toast.LENGTH_SHORT).show()
        tvChickID.text = rawResult?.text
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
            this.setResultHandler(this@DailyEggScanFragment)
            this.setAutoFocus(true)
            this.startCamera()
        }

    }

    private fun initScanView() {

        mScannerView = ZXingScannerView(activity)
        mScannerView!!.setAutoFocus(true)
        mScannerView!!.setResultHandler(this)

        frame_layout_camera.addView(mScannerView)

        frame_layout_camera.setOnClickListener {

            mScannerView!!.resumeCameraPreview(this)
        }
    }

    private fun initListener() {
        etChickID.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                tvChickID.text = s.toString()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                tvChickID.text = s.toString()
            }
        })

        submit.setOnClickListener {
            Log.d("DailyScan", "${checkChickID(tvChickID.text.toString())}")
            when (checkChickID(tvChickID.text.toString())) {
                true -> {
                    var dailyEggQualityFragment = DailyEggQualityFragment.newInstance()
                    Log.d("DailyScan", "chicky ID: ${tvChickID.text}")
                    var bundle = Bundle()
                    bundle.putString("CHICKID", tvChickID.text.toString())
                    bundle.putString("DATE", date)
                    dailyEggQualityFragment.arguments = bundle

                    fragmentManager!!.beginTransaction().replace(R.id.contentFrame, dailyEggQualityFragment).addToBackStack(null).commit()

                }
                false -> {
                    Toast.makeText(context!!, "Incorrect Chick ID", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun checkChickID(chickID: String): Boolean {
        Log.d("DailyScan", "checkChickID $chickID")
        return when (chickID in chickIDList) {
            true -> {
                true
            }
            false -> {
                false
            }
        }
    }

    companion object {
        fun newInstance() = DailyEggScanFragment()
    }
}