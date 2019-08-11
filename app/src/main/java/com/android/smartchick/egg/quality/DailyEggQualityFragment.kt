package com.android.smartchick.egg.quality

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.IntegerRes
import androidx.fragment.app.Fragment
import com.android.smartchick.R
import com.android.smartchick.dashboard.DashboardFragment
import com.android.smartchick.data.DailyEgg
import kotlinx.android.synthetic.main.fragment_daily_egg_quality.*
import java.text.DecimalFormat


class DailyEggQualityFragment : Fragment(), DailyEggQualityContract.View {

    override lateinit var presenter: DailyEggQualityContract.Presenter
    private var chickID: String? = null
    private var date: String? = null
    private var dailyEgg: DailyEgg? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_daily_egg_quality, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.arguments?.apply {
            chickID = this.getString("CHICKID")
            date = this.getString("DATE")
            Log.d("DailyQuality", "Argument chicky ID $chickID, $date")
        }

        dailyEgg = DailyEgg()
        presenter = DailyEggQualityPresenter(this, chickID!!)
        presenter.start()

        initView()

        initListener()
    }

    override fun onLastDailyIDLoaded(ID: String) {
        var intID: Int = Integer.parseInt(ID.replace("DL", ""))
        Log.d("DailyQuality", "LastDailyID: $intID")
        val formatter = DecimalFormat("0000")
        val aFormatted = formatter.format(intID + 1)
        Log.d("DailyQuality", "new DailyID: $aFormatted")
        setDailyID("DL$aFormatted")
    }

    override fun onSuccess() {
        Toast.makeText(context, "Success", Toast.LENGTH_LONG).show()
       // fragmentManager!!.beginTransaction().replace(R.id.contentFrame, DashboardFragment.newInstance()).addToBackStack(null).commit()

    }

    override fun onError(error: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoadingIndicator(active: Boolean) {
        when (active) {
            true -> {
                contentDailyQuality.visibility = View.GONE
                progressBarCalendar.visibility = View.VISIBLE
            }
            false -> {
                contentDailyQuality.visibility = View.VISIBLE
                progressBarCalendar.visibility = View.GONE
            }
        }
    }

    private fun initView() {

        when (chickID.isNullOrEmpty()) {
            true -> {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()

            }
            false -> {
                tvIDChick.text = chickID
                setChickID(chickID!!)
                setDate(date!!)
            }
        }
    }

    private fun initListener() {

        countZero.setOnClickListener {
            setAmountOfEgg(0)
        }

        countOne.setOnClickListener {
            setAmountOfEgg(1)
        }

        countTwo.setOnClickListener {
            setAmountOfEgg(2)
        }

        qNone.setOnClickListener {
            setQualityOfEgg(qNone.text.toString())
        }

        qBad.setOnClickListener {
            setQualityOfEgg(qBad.text.toString())
        }

        qGood.setOnClickListener {
            setQualityOfEgg(qGood.text.toString())
        }

        submitDailyEgg.setOnClickListener {

            presenter.crateDailyEgg(dailyEgg!!)
        }
    }

    private fun setChickID(id: String) {
        dailyEgg!!.id_chick = id
    }

    private fun setDailyID(id: String) {
        dailyEgg!!.id_daily = id
    }

    private fun setAmountOfEgg(count: Int) {
        dailyEgg!!.amount_egg = count

        when (count) {
            0 -> {
                countZero.setBackgroundResource(R.drawable.button_round_pressed)
                countOne.setBackgroundResource(R.drawable.button_round)
                countTwo.setBackgroundResource(R.drawable.button_round)
            }
            1 -> {
                countOne.setBackgroundResource(R.drawable.button_round_pressed)
                countZero.setBackgroundResource(R.drawable.button_round)
                countTwo.setBackgroundResource(R.drawable.button_round)
            }
            2 -> {
                countZero.setBackgroundResource(R.drawable.button_round)
                countOne.setBackgroundResource(R.drawable.button_round)
                countTwo.setBackgroundResource(R.drawable.button_round_pressed)
            }
        }
    }

    private fun setQualityOfEgg(quality: String) {
        dailyEgg!!.quality_egg = quality

        when (quality) {
            qNone.text.toString() -> {
                qNone.setBackgroundResource(R.drawable.button_round_pressed)
                qBad.setBackgroundResource(R.drawable.button_round)
                qGood.setBackgroundResource(R.drawable.button_round)
            }
            qBad.text.toString() -> {
                qBad.setBackgroundResource(R.drawable.button_round_pressed)
                qNone.setBackgroundResource(R.drawable.button_round)
                qGood.setBackgroundResource(R.drawable.button_round)
            }
            qGood.text.toString() -> {
                qBad.setBackgroundResource(R.drawable.button_round)
                qNone.setBackgroundResource(R.drawable.button_round)
                qGood.setBackgroundResource(R.drawable.button_round_pressed)
            }
        }

    }

    private fun setDate(date: String) {
        Log.d("DailtQuality", "Date $date")
        dailyEgg!!.date = date
    }


    companion object {
        fun newInstance() = DailyEggQualityFragment()
    }
}