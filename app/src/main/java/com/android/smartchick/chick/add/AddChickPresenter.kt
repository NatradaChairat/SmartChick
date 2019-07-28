package com.android.smartchick.chick.add

import android.util.Log
import com.android.smartchick.common.GeneratorDBKey
import com.android.smartchick.data.Chicky
import com.android.smartchick.data.Farm
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AddChickPresenter(val view: AddChickContract.View):AddChickContract.Presenter {

    init {
        view.presenter = this
    }

    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

    }

    override fun addChick(chick: Chicky) {
        view.showLoadingIndicator(true)
        Log.d("AddChick", "${GeneratorDBKey().getKey()}, Value Chicky: $chick")
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Chicky").child(GeneratorDBKey().getKey())
        myRef.setValue(chick)
                .addOnSuccessListener {
                    view.showLoadingIndicator(false)
                    view.onSuccess()
                }
                .addOnFailureListener {
                    view.showLoadingIndicator(true)
                }

    }

    override fun loadChickIDList(memberID: String) {
        //LoadChickIDList in Farm of MemberID
        view.showLoadingIndicator(true)

        val database = FirebaseDatabase.getInstance()
        val farmRef = database.getReference("Farm").orderByChild("id_member").equalTo(memberID)
        farmRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var chickIDList = mutableListOf<String?>()
                var resultFarmList = mutableListOf<Farm>()
                resultFarmList.clear()
                dataSnapshot.children.mapNotNullTo(resultFarmList) { it.getValue<Farm>(Farm::class.java) }
                Log.d("AddChick", "Value Farm: $resultFarmList")
                var resultFarmIDList = resultFarmList.map {it.id_farm}.toMutableList()
                Log.d("AddChick", "Value FarmID: $resultFarmIDList")

                for (farmID in resultFarmIDList){
                    val myRef = database.getReference("Chicky").orderByChild("id_farm").equalTo(farmID)
                    myRef.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            var resultChickyList = mutableListOf<Chicky>()
                            resultChickyList.clear()
                            dataSnapshot.children.mapNotNullTo(resultChickyList) { it.getValue<Chicky>(Chicky::class.java) }
                            var list=  resultChickyList.map {it.id_chick}.toMutableList()
                            for (item in list){
                                chickIDList.add(item)
                            }
                            Log.d("AddChick", "Value total chickyID: $chickIDList")

                        }

                        override fun onCancelled(error: DatabaseError) {
                            // Failed to read value
                            Log.w("AddChick", "Failed to read value.", error.toException())
                            view.onError("Failed to read value.")
                            view.showLoadingIndicator(false)
                        }
                    })
                }

                view.onChickIDListLoaded(chickIDList)
                view.showLoadingIndicator(false)
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("AddChick", "Failed to read value.", error.toException())
                view.onError("Failed to read value.")
                view.showLoadingIndicator(false)
            }
        })
    }

    override fun loadFarmList(memberID: String) {
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Farm").orderByChild("id_member").equalTo(memberID)
        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var resultList = mutableListOf<Farm>()
                resultList.clear()
                dataSnapshot.children.mapNotNullTo(resultList) { it.getValue<Farm>(Farm::class.java) }
                Log.d("AddChick", "Value farm: $resultList")
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