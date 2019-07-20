package com.android.smartchick.dashboard

import android.util.Log
import com.android.smartchick.data.Member
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*

class DashboardPresenter (val view: DashboardContract.View,
                          var memberID: String): DashboardContract.Presenter {


    init {
        view.presenter = this
    }
    override fun start() {
        loadInformation(memberID)
    }

    override fun loadInformation(memberId: String) {
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Member").orderByChild("id_member").equalTo(memberId)
        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var resultList = mutableListOf<Member>()

                dataSnapshot.children.mapNotNullTo(resultList) { it.getValue<Member>(Member::class.java) }
                Log.d("DashBoard", "Value res: $resultList")
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("DashBoard", "Failed to read value.", error.toException())
            }
        })
    }
}