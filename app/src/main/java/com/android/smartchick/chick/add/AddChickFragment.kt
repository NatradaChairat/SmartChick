package com.android.smartchick.chick.add

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.smartchick.R
import com.google.zxing.Result
import kotlinx.android.synthetic.main.fragment_scan_add_chick.*
import me.dm7.barcodescanner.zxing.ZXingScannerView

class AddChickFragment : Fragment(), ZXingScannerView.ResultHandler  {

    private var mScannerView: ZXingScannerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_scan_add_chick, container, false)
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
        fun newInstance() = AddChickFragment()
    }
}