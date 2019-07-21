package com.android.smartchick.farm

import android.util.Log
import com.android.smartchick.data.Farm
import com.android.smartchick.data.Member
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FarmInformationPresenter(val view: FarmInformationContract.View,
                               var memberID: String): FarmInformationContract.Presenter {

    init {
        view.presenter = this
    }

    override fun start() {
        loadInformation(memberID)
        loadFarmInformation(memberID)
    }

    override fun loadInformation(memberId: String) {

        val database = FirebaseDatabase.getInstance()
        val myRefMember = database.getReference("Member").orderByChild("id_member").equalTo(memberId)
        myRefMember.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var resultList = mutableListOf<Member>()
                resultList.clear()
                dataSnapshot.children.mapNotNullTo(resultList) { it.getValue<Member>(Member::class.java) }
                Log.d("FarmInfo", "Value res: $resultList")
                view.onMemberLoaded(resultList[0])
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("FarmInfo", "Failed to read value.", error.toException())
                view.onError("Failed to read value.")
            }
        })
    }

    override fun loadFarmInformation(memberId: String){

        val database = FirebaseDatabase.getInstance()
        val myRefFarm = database.getReference("Farm").orderByChild("id_member").equalTo(memberId)
        myRefFarm.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var resultList = mutableListOf<Farm>()
                resultList.clear()
                dataSnapshot.children.mapNotNullTo(resultList) { it.getValue<Farm>(Farm::class.java) }
                Log.d("FarmInfo", "Value farm: $resultList")
                view.onFarmLoaded(resultList)
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("FarmInfo", "Failed to read value.", error.toException())
                view.onError("Failed to read value.")
            }
        })
    }

}