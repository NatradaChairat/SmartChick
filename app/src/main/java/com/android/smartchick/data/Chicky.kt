package com.android.smartchick.data

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import java.util.*

@IgnoreExtraProperties
data class Chicky (
        var id_chick: String? = null,
        var id_farm: String?= null,
        var vaccine: Int? = null,
        var weight: Int? =null,
        var birthdate: String? =null
){
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
                "id_farm" to id_farm,
                "id_chick" to id_chick,
                "vaccine" to vaccine,
                "weight" to weight,
                "birthdate" to birthdate
        )
    }
}