package com.android.smartchick.chick.add

import android.content.Context
import android.content.SharedPreferences
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
import com.android.smartchick.authentication.login.MEMBER
import com.android.smartchick.authentication.login.MEMBER_ID
import com.android.smartchick.chick.add.info.AddChickInfoFragment
import com.android.smartchick.data.Farm
import com.google.zxing.Result
import kotlinx.android.synthetic.main.fragment_scan_add_chick.*
import kotlinx.android.synthetic.main.fragment_scan_add_chick.frame_layout_camera
import me.dm7.barcodescanner.zxing.ZXingScannerView

class AddChickFragment : Fragment(), AddChickContract.View, ZXingScannerView.ResultHandler  {

    override lateinit var presenter: AddChickContract.Presenter

    private var mScannerView: ZXingScannerView? = null
    private var chickIDs = mutableListOf<String?>()
    private var memberID : String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        presenter = AddChickPresenter(this)

        var sharedPref: SharedPreferences = activity!!.getSharedPreferences(MEMBER, Context.MODE_PRIVATE)
        memberID = sharedPref.getString(MEMBER_ID, null)

        return inflater.inflate(R.layout.fragment_scan_add_chick, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        memberID?.apply {
            presenter.loadChickIDList(this)
        }

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
            this.setResultHandler(this@AddChickFragment)
            this.setAutoFocus(true)
            this.startCamera()
        }

    }

    override fun handleResult(rawResult: Result?) {
        Log.d("QRcode Scanner", "Read: ${rawResult?.text}")
        Toast.makeText(context!!, "${rawResult?.text}", Toast.LENGTH_SHORT).show()
        tvAddChickID.text = rawResult?.text
    }

    override fun onSuccess() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onChickIDListLoaded(result: MutableList<String?>) {
        chickIDs = result
    }

    override fun onFarmLoaded(result: MutableList<Farm>) {

    }


    override fun onError(error: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoadingIndicator(active: Boolean) {
        when (active) {
            true -> {
                llScanAddChick.visibility = View.GONE
                progressBarScanAddChick.visibility = View.VISIBLE
            }
            false -> {
                llScanAddChick.visibility = View.VISIBLE
                progressBarScanAddChick.visibility = View.GONE
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

    private fun initListener() {
        etAddChickID.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                tvAddChickID.text = s.toString()
            }
        })

        nextAddChick.setOnClickListener {
            Log.d("AddChick", "Is duplicate: ${checkChickIDIsDuplicate(tvAddChickID.text.toString())}")
            when (!checkChickIDIsDuplicate(tvAddChickID.text.toString())) {
                true -> {
                    var addChickInfoFragment = AddChickInfoFragment.newInstance()
                    Log.d("AddChick", "chicky ID: ${tvAddChickID.text}")
                    var bundle = Bundle()
                    bundle.putString("CHICKID", tvAddChickID.text.toString())
                    addChickInfoFragment.arguments = bundle

                    fragmentManager!!.beginTransaction().replace(R.id.contentFrame, addChickInfoFragment).addToBackStack(null).commit()

                }
                false -> {
                    Toast.makeText(context!!, "Chick ID is duplicate", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun checkChickIDIsDuplicate(chickID: String): Boolean {
        Log.d("AddChick", "checkChickID $chickID")
        return when (chickID in chickIDs) {
            true -> {
                true
            }
            false -> {
                false
            }
        }
        return true
    }

    companion object {
        fun newInstance() = AddChickFragment()
    }
}