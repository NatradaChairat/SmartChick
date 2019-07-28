package com.android.smartchick.farm

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.smartchick.R
import com.android.smartchick.authentication.login.MEMBER
import com.android.smartchick.authentication.login.MEMBER_ID
import com.android.smartchick.data.Farm
import com.android.smartchick.data.Member
import kotlinx.android.synthetic.main.fragment_farm_information.*


class FarmInformationFragment : Fragment(), FarmInformationContract.View {

    override lateinit var presenter: FarmInformationContract.Presenter
    private lateinit var adapter: FarmInformationAdapter

    private var memberID: String? = null
    private var member: Member? = null
    private var farms: MutableList<Farm> = mutableListOf()
    private var farmNameList: MutableList<String?> = mutableListOf()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var sharedPref: SharedPreferences = activity!!.getSharedPreferences(MEMBER, Context.MODE_PRIVATE)
        memberID = sharedPref.getString(MEMBER_ID, null)

        presenter = FarmInformationPresenter(this, memberID!!)

        return inflater.inflate(R.layout.fragment_farm_information, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.start()

        initRecyclerView()

    }

    override fun onFarmLoaded(farmsResult: MutableList<Farm>) {
        farms = farmsResult
        farmNameList = farmsResult.map { it.name_farm }.toMutableList()

        adapter.farms = farmNameList
        initView()
    }

    override fun onMemberLoaded(memberResult: Member) {
        member = memberResult
    }

    override fun onError(error: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoadingIndicator(active: Boolean) {
        when(active){
            true -> {
                llFarmInfo.visibility = View.GONE
                progressBarFarmInfo.visibility = View.VISIBLE
            }
            false -> {
                llFarmInfo.visibility = View.VISIBLE
                progressBarFarmInfo.visibility = View.GONE
            }
        }

    }

    private fun initView() {
        member?.apply {
            tvName.text = "คุณ ${this.name_member}"
            tvFarmName.text = "ชื่อฟาร์ม ${this.name_farm}"
            tvAddress.text = "ที่อยู่ ${this.address}"
            tvPhoneNumber.text = "เบอร์โทรติต่อ ${this.telephone}"
        }

    }

    private fun initRecyclerView(){
        adapter = FarmInformationAdapter(farmNameList = farmNameList)

        with(farmRecyclerView) {
            setHasFixedSize(true)
            this.adapter = this@FarmInformationFragment.adapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun initListenner() {

    }

    companion object {
        fun newInstance() = FarmInformationFragment()
    }
}