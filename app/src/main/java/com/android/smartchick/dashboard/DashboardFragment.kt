package com.android.smartchick.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.smartchick.R
import com.android.smartchick.chick.profile.ProfileChickFragment
import com.android.smartchick.farm.FarmInformationFragment
import kotlinx.android.synthetic.main.fragment_main.*

class DashboardFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
    }

    private fun initListener(){

        btnProfileChick.setOnClickListener {
            fragmentManager!!.beginTransaction().replace(R.id.contentFrame, ProfileChickFragment.newInstance()).addToBackStack(null).commit()
        }

        btnInfoFarm.setOnClickListener {
            fragmentManager!!.beginTransaction().replace(R.id.contentFrame, FarmInformationFragment.newInstance()).addToBackStack(null).commit()
        }
    }

    companion object {
        fun newInstance() = DashboardFragment()
    }
}