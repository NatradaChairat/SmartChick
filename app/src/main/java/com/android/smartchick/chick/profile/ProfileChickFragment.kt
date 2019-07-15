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


class ProfileChickFragment : Fragment() {

    lateinit var captureManager: CaptureManager
    var scanState: Boolean = false
    var torchState: Boolean = false




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_scan_profile_chick, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        val s = CameraSettings()
        s.requestedCameraId = 0
        barcodeView.barcodeView.cameraSettings = s
        barcodeView.resume()

        barcodeView.decodeSingle(object : BarcodeCallback {
            override fun barcodeResult(result: BarcodeResult) {
                Log.d("ProfileChick ","barcode result: $result")
                // do your thing with result
            }

            override fun possibleResultPoints(resultPoints: List<ResultPoint>) {}
        })
//        captureManager = CaptureManager(activity, barcodeView)
//        captureManager.initializeFromIntent(intent, savedInstanceState)

//        btnScan.setOnClickListener {
//            txtResult.text = "scaning..."
//            barcodeView.decodeSingle(object: BarcodeCallback {
//                override fun barcodeResult(result: BarcodeResult?) {
//                    result?.let {
//                        txtResult.text = it.text
//
//                        val vib: Vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
//
//                        if(vib.hasVibrator()){
//                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//                                // void vibrate (VibrationEffect vibe)
//                                vib.vibrate(
//                                        VibrationEffect.createOneShot(
//                                                100,
//                                                // The default vibration strength of the device.
//                                                VibrationEffect.DEFAULT_AMPLITUDE
//                                        )
//                                )
//                            }else{
//                                // This method was deprecated in API level 26
//                                vib.vibrate(100)
//                            }
//                        }
//                    }
//                }
//
//                override fun possibleResultPoints(resultPoints: MutableList<ResultPoint>?) {
//                }
//            })
//        }
//
//        btnTorch.setOnClickListener {
//            if(torchState){
//                torchState = false
//                barcodeView.setTorchOff()
//            } else {
//                torchState = true
//                barcodeView.setTorchOn()
//            }
//        }
    }

    companion object {
        fun newInstance() = ProfileChickFragment()
    }
}