package com.android.smartchick.egg

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.smartchick.R
import com.android.smartchick.chick.add.AddChickFragment
import com.google.zxing.Result
import kotlinx.android.synthetic.main.fragment_scan_daily_egg.*
import me.dm7.barcodescanner.zxing.ZXingScannerView

class DailyEggFragment : Fragment() , ZXingScannerView.ResultHandler {

    private var mScannerView: ZXingScannerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_daily_egg, container, false)
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
            this.setResultHandler(this@DailyEggFragment)
            this.setAutoFocus(true)
            this.startCamera()
        }

    }

    override fun handleResult(rawResult: Result?) {
        Log.d("QRcode Scanner", "Read: ${rawResult?.text}")
        Toast.makeText(context!!, "${rawResult?.text}", Toast.LENGTH_SHORT).show()
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

    companion object {
        fun newInstance() = DailyEggFragment()
    }
}