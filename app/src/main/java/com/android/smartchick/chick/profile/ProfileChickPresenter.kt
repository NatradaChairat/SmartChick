package com.android.smartchick.chick.profile

import android.util.Log
import com.android.smartchick.data.Chicky
import com.android.smartchick.data.Farm
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class ProfileChickPresenter(val view: ProfileChickContract.View,
                            val memberID: String): ProfileChickContract.Presenter {

    init {
        view.presenter = this
    }

    override fun start() {

        Log.d("Profile", "Presenter start")
        loadFarmIDFromMemberID(memberID)
    }

    override fun loadChick(chickID: String, farmIdList: MutableList<String?>) {
        //Load Chick that id_chick child is equal with chickID and id_farm are in farmID
        view.showLoadingIndicator(true)
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Chicky").orderByChild("id_chick").equalTo(chickID)
        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            //First get Chick that id_chick equal to chickID
            //Next check Chick in Farm or not?
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var resultChickyList = mutableListOf<Chicky>()
                resultChickyList.clear()
                dataSnapshot.children.mapNotNullTo(resultChickyList) { it.getValue<Chicky>(Chicky::class.java) }
                Log.d("Profile", "Value total chickyID: $resultChickyList")
                var matchChick: Chicky? = null

                for(chick in resultChickyList){
                    Log.d("Profile", "Value ${chick.id_farm} farmIds: $farmIdList")
                    if(farmIdList.contains(chick.id_farm)){
                        Log.d("Profile", "Value chick: $chick")
                        matchChick = chick
                    }
                }

                view.showLoadingIndicator(false)
                if(matchChick != null){
                    Log.d("Profile", "Match chick")
                    view.onChickLoaded(matchChick)
                }else {
                    view.onError("Chick ID not matching")
                }

            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("Profile", "Failed to read value.", error.toException())
                view.onError("Failed to read value.")
                view.showLoadingIndicator(false)
            }
        })

    }

    private fun loadFarmIDFromMemberID(memberID: String){
        Log.d("Profile", "loadFarmIDFromMemberID")
        view.showLoadingIndicator(true)
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Farm").orderByChild("id_member").equalTo(memberID)
        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var resultFarmList = mutableListOf<Farm>()
                resultFarmList.clear()
                dataSnapshot.children.mapNotNullTo(resultFarmList) { it.getValue<Farm>(Farm::class.java) }
                Log.d("Profile", "Value total Farm: ${resultFarmList.map {it.id_farm}.toMutableList()}")
                var farmIDList = resultFarmList.map {it.id_farm}.toMutableList()

                view.onFarmIdLoaded(farmIDList)
                view.showLoadingIndicator(false)
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("Profile", "Failed to read value.", error.toException())
                view.onError("Failed to read value.")
                view.showLoadingIndicator(false)
            }
        })

    }

}