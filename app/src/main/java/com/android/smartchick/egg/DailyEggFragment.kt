package com.android.smartchick.egg

import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.smartchick.R
import com.android.smartchick.chick.add.AddChickFragment
import com.android.smartchick.data.Chicky
import com.android.smartchick.data.Farm
import com.android.smartchick.egg.scan.DailyEggScanFragment
import com.google.zxing.Result
import kotlinx.android.synthetic.main.fragment_daily_egg.*
import kotlinx.android.synthetic.main.fragment_scan_daily_egg.*
import me.dm7.barcodescanner.zxing.ZXingScannerView
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class DailyEggFragment : Fragment(), DailyEggContract.View , ZXingScannerView.ResultHandler {

    override lateinit var presenter: DailyEggContract.Presenter
    private var memberID : String? = null
    private var farms: MutableList<Farm> = mutableListOf()
    private var chickys: MutableList<Chicky> = mutableListOf()
    private var chickIDList: MutableList<String?> = mutableListOf()

    private var finalDate: String = ""
    private var isSelected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        this.arguments?.let {
            memberID = it.getString("MEMBER_ID")
        }
        presenter = DailyEggPresenter(this, memberID!!)
        return inflater.inflate(R.layout.fragment_daily_egg, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.start()
    }

    override fun onFarmLoaded(result: MutableList<Farm>) {
        farms = result
        Log.d("DailyEgg", "onFarmLoad${result.map {it.name_farm}}")

        initSpinner(result.map {it.name_farm}.toMutableList())
        initView()
        initListener()
    }

    override fun onChickyLoaded(result: MutableList<Chicky>) {
        chickys = result
        chickIDList = result.map {it.id_chick}.toMutableList()
        Log.d("DailyEgg", "onChickyLoad$chickIDList")
    }

    override fun onError(error: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoadingIndicator(active: Boolean) {
        when (active) {
            true -> progressBarCalendar.visibility = View.VISIBLE
            false -> progressBarCalendar.visibility = View.GONE
        }
    }

    override fun handleResult(rawResult: Result?) {
        Log.d("QRcode Scanner", "Read: ${rawResult?.text}")
        Toast.makeText(context!!, "${rawResult?.text}", Toast.LENGTH_SHORT).show()
    }

    private fun initSpinner(farmNameList: MutableList<String?>){

        val adapter = ArrayAdapter(
                context,
                android.R.layout.simple_spinner_item,
                farmNameList
        )

        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)

        spinner.adapter = adapter
    }

    private fun initView() {

        val calendarIns = Calendar.getInstance()
        calendarIns.timeInMillis = calendar.date
        val Year = calendarIns.get(Calendar.YEAR)
        val Month = calendarIns.get(Calendar.MONTH)
        val Day = calendarIns.get(Calendar.DAY_OF_MONTH)
        finalDate = when (Month < 9) {
            true -> {
                when (Day < 10){
                    true -> "0$Day/0${Month+1}/$Year"
                    false -> "$Day/0${Month+1}/$Year"
                }

            }
            false -> {
                when (Day < 10){
                    true -> "0$Day/${Month+1}/$Year"
                    false -> "$Day/${Month+1}/$Year"
                }
            }
        }

        Log.d("DailyEgg", "First Date $finalDate")
        contentDaily.visibility = View.VISIBLE
    }

    private fun initListener(){

        calendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
            finalDate = when (month < 9) {
                true -> {
                    when (dayOfMonth < 10){
                        true -> "0$dayOfMonth/0${month+1}/$year"
                        false -> "$dayOfMonth/0${month+1}/$year"
                    }

                }
                false -> {
                    when (dayOfMonth < 10){
                        true -> "0$dayOfMonth/${month+1}/$year"
                        false -> "$dayOfMonth/${month+1}/$year"
                    }
                }
            }
            Log.d("DailyEgg", "DateChangeTo $finalDate")
        }


        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent:AdapterView<*>, view: View, position: Int, id: Long){
                Log.d("DailyEgg ","Selected ${parent.getItemAtPosition(position)}")
                isSelected = true
                farms.filter { it.name_farm == parent.getItemAtPosition(position) }[0].id_farm?.let {
                    presenter.loadChickyInformation(it)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>){
                isSelected = false
                Toast.makeText(context, "Please select farm", Toast.LENGTH_LONG).show()
            }
        }

        next.setOnClickListener {
            when (isSelected) {
                true -> {
                    onNextClicked()
                }
                false -> {
                    Toast.makeText(context, "Please select farm", Toast.LENGTH_LONG).show()
                }
            }

        }
    }

    private fun onNextClicked(){
        when (chickIDList.isNullOrEmpty()){
            true -> {
                Toast.makeText(context, "No Chick in this farm", Toast.LENGTH_LONG).show()
            }
            false -> {
                var dailyEggScanFragment = DailyEggScanFragment.newInstance()
                Log.d("DailyEgg", "Next chicky: $chickIDList")
                chickIDList?.apply {
                    var bundle = Bundle()
                    bundle.putStringArray("CHICKID_ARRAY", this.toTypedArray())
                    bundle.putString("DATE", finalDate)
                    dailyEggScanFragment.arguments = bundle
                }
                fragmentManager!!.beginTransaction().replace(R.id.contentFrame, dailyEggScanFragment).addToBackStack(null).commit()
            }
        }
    }

    companion object {
        fun newInstance() = DailyEggFragment()
    }
}