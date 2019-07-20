package com.android.smartchick.dashboard

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.android.smartchick.R
import com.android.smartchick.chick.add.AddChickFragment
import com.android.smartchick.chick.profile.ProfileChickFragment
import com.android.smartchick.data.Member
import com.android.smartchick.egg.DailyEggFragment
import com.android.smartchick.farm.FarmInformationFragment
import kotlinx.android.synthetic.main.fragment_main.*

class DashboardFragment : Fragment(), DashboardContract.View {

    override lateinit var presenter: DashboardContract.Presenter
    private var memberID : String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.show()

        this.arguments?.let {
            memberID = it.getString("MEMBER_ID")
        }

        presenter = DashboardPresenter(this, memberID!!)
        presenter.start()
        initListener()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        return inflater.inflate(R.menu.manu_main, menu)

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

    override fun onResultLoaded(member: Member) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onError(error: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    private fun initListener(){

        btnProfileChick.setOnClickListener {
            fragmentManager!!.beginTransaction().replace(R.id.contentFrame, ProfileChickFragment.newInstance()).addToBackStack(null).commit()
        }

        btnInfoFarm.setOnClickListener {
            fragmentManager!!.beginTransaction().replace(R.id.contentFrame, FarmInformationFragment.newInstance()).addToBackStack(null).commit()
        }

        btnDailyEgg.setOnClickListener {
            fragmentManager!!.beginTransaction().replace(R.id.contentFrame, DailyEggFragment.newInstance()).addToBackStack(null).commit()
        }

        btnAddChick.setOnClickListener {
            fragmentManager!!.beginTransaction().replace(R.id.contentFrame, AddChickFragment.newInstance()).addToBackStack(null).commit()
        }

    }

    companion object {
        fun newInstance() = DashboardFragment()
    }
}