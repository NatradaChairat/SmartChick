package com.android.smartchick.dashboard

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.android.smartchick.R
import com.android.smartchick.authentication.login.MEMBER
import com.android.smartchick.authentication.login.MEMBER_ID
import com.android.smartchick.chick.add.AddChickFragment
import com.android.smartchick.chick.profile.ProfileChickFragment
import com.android.smartchick.data.Member
import com.android.smartchick.egg.DailyEggFragment
import com.android.smartchick.farm.FarmInformationFragment
import kotlinx.android.synthetic.main.fragment_main.*

class DashboardFragment(var memberID: String) : Fragment(), DashboardContract.View {

    override lateinit var presenter: DashboardContract.Presenter
    private var member: Member? =null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

//        this.arguments?.let {
//            memberID = it.getString("MEMBER_ID")
//        }
//
//        when(memberID.isNullOrEmpty()) {
//            true -> {
//                var sharedPref: SharedPreferences = activity!!.getSharedPreferences(MEMBER, MODE_PRIVATE)
//                memberID = sharedPref.getString(MEMBER_ID, null)
//            }
//        }
        Log.d("DashBoard", "$memberID")

        presenter = DashboardPresenter(this, memberID)

        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.show()

        presenter.start()
        initListener()
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        return inflater.inflate(R.menu.menu_main, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        item?.apply {
            when (this.itemId) {
                R.id.profile -> {

                }

                R.id.notification -> {

                }

                android.R.id.home -> {

                }

            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onResultLoaded(result: Member) {
        member = result
        initView()
    }

    override fun onError(error: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoadingIndicator(active: Boolean){
        when (active) {
            true -> progressBarCalendar.visibility = View.VISIBLE
            false -> progressBarCalendar.visibility = View.GONE
        }
    }

    private fun initView(){
        tvName.text = member!!.name_member
        tvFarmName.text = member!!.name_farm
        mainLayout.visibility = View.VISIBLE
    }


    private fun initListener(){

        btnProfileChick.setOnClickListener {
            fragmentManager!!.beginTransaction().replace(R.id.contentFrameDashBoard, ProfileChickFragment.newInstance()).addToBackStack(null).commit()
        }

        btnInfoFarm.setOnClickListener {
            fragmentManager!!.beginTransaction().replace(R.id.contentFrameDashBoard, FarmInformationFragment.newInstance()).addToBackStack(null).commit()
        }

        btnDailyEgg.setOnClickListener {
            var dailyEggFragment = DailyEggFragment.newInstance()
            memberID?.apply {
                var bundle = Bundle()
                bundle.putString("MEMBER_ID", memberID)
                dailyEggFragment.arguments = bundle
            }
            fragmentManager!!.beginTransaction().replace(R.id.contentFrameDashBoard, dailyEggFragment).addToBackStack(null).commit()
        }

        btnAddChick.setOnClickListener {
            fragmentManager!!.beginTransaction().replace(R.id.contentFrameDashBoard, AddChickFragment.newInstance()).addToBackStack(null).commit()
        }

    }

    companion object {
        fun newInstance(memberID: String) = DashboardFragment(memberID)
    }
}