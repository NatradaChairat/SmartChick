package com.android.smartchick.chick.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.smartchick.R
import com.android.smartchick.data.Chicky
import kotlinx.android.synthetic.main.fragment_profile_chick.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*




class ProfileInfoChickFragment : Fragment(){

    private var chick= Chicky()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile_chick, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.arguments?.apply {
            chick.id_chick = this.getString("CHICKID")
            chick.id_farm = this.getString("FARMID")
            chick.birthdate = this.getString("BOD")
            chick.weight = this.getInt("WEIGHT")
            chick.vaccine = this.getString("VACCINE")
            Log.d("Profile", "Argument chicky ID $chick")
        }

        initView()
        initListener()
    }

    private fun initView(){
        infoProfileID.text = chick.id_chick
        tvBOD.text = chick.birthdate
        tvAge.text = getAgeFromBirthdate(chick.birthdate!!) + " days"
        tvVaccine.text = chick.vaccine
        tvWeight.text = chick.weight.toString()

    }

    private fun initListener(){

    }

    private fun getAgeFromBirthdate(birthdate: String): String{
        var date: Date? = null
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        try {
            date = sdf.parse(birthdate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        val dob = Calendar.getInstance()
        val today = Calendar.getInstance()

        dob.time = date

        val year = dob.get(Calendar.YEAR)
        val month = dob.get(Calendar.MONTH)
        val day = dob.get(Calendar.DAY_OF_MONTH)

        dob.set(year, month, day)


        // Get the represented date in milliseconds
        val millis1 = dob.timeInMillis
        val millis2 = today.timeInMillis

        // Calculate difference in milliseconds
        val diff = millis2 - millis1

        var diffDays = diff / (24 * 60 * 60 * 1000)

        return diffDays.toString()
    }

    companion object {
        fun newInstance() = ProfileInfoChickFragment()
    }
}