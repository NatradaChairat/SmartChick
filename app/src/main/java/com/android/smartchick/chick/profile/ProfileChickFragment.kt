package com.android.smartchick.chick.profile

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.smartchick.R
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.CaptureManager
import kotlinx.android.synthetic.main.fragment_scan_profile_chick.*
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.camera.CameraSettings
import android.util.Log
import android.widget.Toast
import me.dm7.barcodescanner.zxing.ZXingScannerView
import com.google.zxing.Result


class ProfileChickFragment : Fragment(), ZXingScannerView.ResultHandler  {


    lateinit var captureManager: CaptureManager
    var scanState: Boolean = false
    var torchState: Boolean = false

    private var mScannerView: ZXingScannerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_scan_profile_chick, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mScannerView = ZXingScannerView(activity)
        mScannerView!!.setAutoFocus(true)
        mScannerView!!.setResultHandler(this)
        frame_layout_camera.addView(mScannerView)
        initView()
    }

    override fun onStart() {
        super.onStart()
        mScannerView!!.startCamera()
    }

    override fun onPause() {
        super.onPause()
        mScannerView!!.stopCamera()
    }

    override fun onResume() {
        super.onResume()
        mScannerView!!.setResultHandler(this)
        mScannerView!!.setAutoFocus(true)
        mScannerView!!.startCamera()
    }

    override fun handleResult(rawResult: Result?) {
        Log.d("QRcode Scanner", "Read: ${rawResult?.text}")
        Toast.makeText(context!!, "${rawResult?.text}", Toast.LENGTH_SHORT).show()
    }

    private fun initView() {

        frame_layout_camera.setOnClickListener {

            mScannerView!!.resumeCameraPreview(this)
        }

    }

    companion object {
        fun newInstance() = ProfileChickFragment()
    }
}