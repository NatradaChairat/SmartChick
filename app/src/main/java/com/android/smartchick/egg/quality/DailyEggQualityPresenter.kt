package com.android.smartchick.egg.quality

import android.annotation.TargetApi
import android.os.Build
import android.util.Log
import com.android.smartchick.data.DailyEgg
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DailyEggQualityPresenter(val view: DailyEggQualityContract.View,
                               var chickID: String) : DailyEggQualityContract.Presenter {

    init {
        view.presenter = this
    }

    override fun start() {
        getLastDailyEgg(chickID)
    }

    override fun getLastDailyEgg(chickID: String) {
        view.showLoadingIndicator(true)
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("DailyEgg").orderByChild("id_chick").equalTo(chickID)
        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var resultList = mutableListOf<DailyEgg>()
                resultList.clear()
                dataSnapshot.children.mapNotNullTo(resultList) { it.getValue<DailyEgg>(DailyEgg::class.java) }
                resultList.sortBy { it.id_daily }
                Log.d("DailyQuality", "Value Last: ${resultList.lastIndex}")
                when(resultList.size == 0){
                    true -> {
                        view.onLastDailyIDLoaded("DL0000")
                    }
                    false -> {
                        view.onLastDailyIDLoaded(resultList[resultList.size - 1].id_daily!!)
                    }
                }
                view.showLoadingIndicator(false)
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("DashBoard", "Failed to read value.", error.toException())
                view.onError("Failed to read value.")
                view.showLoadingIndicator(false)
            }
        })
    }

    @TargetApi(Build.VERSION_CODES.O)
    override fun crateDailyEgg(dailyEgg: DailyEgg) {
        view.showLoadingIndicator(true)
        Log.d("DailyQuality", "Value DailyEgg: $dailyEgg")

        var localDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("ddMMyyyyHHmmssSSS")
        val key = formatter.format(localDateTime)
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("DailyEgg").child(key)
        myRef.setValue(dailyEgg)
                .addOnSuccessListener {
                    view.showLoadingIndicator(false)
                    view.onSuccess()
                }
                .addOnFailureListener {
                    view.showLoadingIndicator(true)
                }


    }
}