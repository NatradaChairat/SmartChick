package com.android.smartchick.egg

import android.util.Log
import com.android.smartchick.data.Chicky
import com.android.smartchick.data.DailyEgg
import com.android.smartchick.data.Farm
import com.android.smartchick.data.Member
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DailyEggPresenter(val view: DailyEggContract.View,
                        var memberID: String): DailyEggContract.Presenter {

    init {
        view.presenter = this
    }
    override fun start() {
        view.showLoadingIndicator(true)
        loadFarmInformation(memberID)
    }

    override fun loadChickyInformation(farmID: String) {
        view.showLoadingIndicator(true)
        Log.d("DailyEgg", "Value farmID: $farmID" +
                "")
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Chicky").orderByChild("id_farm").equalTo(farmID)
        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var resultList = mutableListOf<Chicky>()
                resultList.clear()
                dataSnapshot.children.mapNotNullTo(resultList) { it.getValue<Chicky>(Chicky::class.java) }
                Log.d("DailyEgg", "Value chicky: $resultList")
                view.onChickyLoaded(resultList)
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

    override fun loadFarmInformation(memberID: String) {
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Farm").orderByChild("id_member").equalTo(memberID)
        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var resultList = mutableListOf<Farm>()
                resultList.clear()
                dataSnapshot.children.mapNotNullTo(resultList) { it.getValue<Farm>(Farm::class.java) }
                Log.d("DailyEgg", "Value farm: $resultList")
                view.onFarmLoaded(resultList)
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

}