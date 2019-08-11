package com.android.smartchick.chick.add.info

import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.smartchick.R
import com.android.smartchick.authentication.login.MEMBER
import com.android.smartchick.authentication.login.MEMBER_ID
import com.android.smartchick.chick.add.AddChickContract
import com.android.smartchick.chick.add.AddChickPresenter
import com.android.smartchick.dashboard.DashboardFragment
import com.android.smartchick.data.Chicky
import com.android.smartchick.data.Farm
import kotlinx.android.synthetic.main.fragment_add_chick.*
import java.util.*

class AddChickInfoFragment : Fragment(), AddChickContract.View{

    override lateinit var presenter: AddChickContract.Presenter
    private var chickID: String? = null
    private var farmID: String? = null
    private var memberID : String? = null
    private var isSelected = false
    private var farms: MutableList<Farm> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var sharedPref: SharedPreferences = activity!!.getSharedPreferences(MEMBER, Context.MODE_PRIVATE)
        memberID = sharedPref.getString(MEMBER_ID, null)
        presenter = AddChickPresenter(this)

        return inflater.inflate(R.layout.fragment_add_chick, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.arguments?.apply {
            chickID = this.getString("CHICKID")
            Log.d("AddChick", "Argument chicky ID $chickID")
        }

        memberID?.apply {
            presenter.loadFarmList(this)
        }

        initView()
        initListener()

    }

    override fun onSuccess() {
        Toast.makeText(context, "Adding success", Toast.LENGTH_LONG).show()
       // fragmentManager!!.beginTransaction().replace(R.id.contentFrame, DashboardFragment.newInstance()).addToBackStack(null).commit()
    }

    override fun onChickIDListLoaded(result: MutableList<String?>) {

    }

    override fun onFarmLoaded(result: MutableList<Farm>) {
        Log.d("AddChick", "onFarmLoad${result.map {it.name_farm}}")
        farms = result
        initSpinner(result.map {it.name_farm}.toMutableList())
    }

    override fun onError(error: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoadingIndicator(active: Boolean) {
        when (active) {
            true -> {
                llAddChick.visibility = View.GONE
                progressBarAddChick.visibility = View.VISIBLE
            }
            false -> {
                llAddChick.visibility = View.VISIBLE
                progressBarAddChick.visibility = View.GONE
            }
        }
    }

    private fun initView(){
        infoIDAddChick.text = chickID
    }

    private fun initSpinner(farmNameList: MutableList<String?>){

        val adapter = ArrayAdapter(
                context,
                android.R.layout.simple_spinner_item,
                farmNameList
        )

        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)

        spinnerFarm.adapter = adapter
    }

    private fun initListener(){
        spinnerFarm.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long){
                Log.d("AddChick ","Selected ${parent.getItemAtPosition(position)}")
                isSelected = true
                farms.filter { it.name_farm == parent.getItemAtPosition(position) }[0].id_farm?.let {
                    farmID = it
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>){
                isSelected = false
                Toast.makeText(context, "Please select farm", Toast.LENGTH_LONG).show()
            }
        }

        etBOD.setOnClickListener {
            val cldr: Calendar = Calendar.getInstance()
            var day = cldr.get(Calendar.DAY_OF_MONTH)
            var month = cldr.get(Calendar.MONTH)
            var year = cldr.get(Calendar.YEAR)
            // date picker dialog
            var picker = DatePickerDialog(context, object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
                    var finalDate = when (monthOfYear < 9) {
                        true -> {
                            when (dayOfMonth < 10){
                                true -> "0$dayOfMonth/0${monthOfYear+1}/$year"
                                false -> "$dayOfMonth/0${monthOfYear+1}/$year"
                            }

                        }
                        false -> {
                            when (dayOfMonth < 10){
                                true -> "0$dayOfMonth/${monthOfYear+1}/$year"
                                false -> "$dayOfMonth/${monthOfYear+1}/$year"
                            }
                        }
                    }
                    etBOD.setText("$finalDate")
                }
            }, year, month, day)
            picker.show()

        }
        btnAddChickInfo.setOnClickListener {
            var newChick = Chicky()
            Log.d("AddChick", "$chickID, $farmID, ${etWeight.text}, ${etBOD.text}, ${etVaccine.text}")
            newChick.birthdate = etBOD.text.toString()
            newChick.id_chick = chickID
            newChick.id_farm = farmID
            newChick.weight = Integer.parseInt(etWeight.text.toString())
            newChick.vaccine = etVaccine.text.toString()
            presenter.addChick(newChick)
        }
    }

    companion object {
        fun newInstance() = AddChickInfoFragment()
    }

}