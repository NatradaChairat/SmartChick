package com.android.smartchick.data

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Farm(
        var id_farm: String?=null,
        var id_member: String?=null,
        var name_farm: String? =null
) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
                "id_farm" to id_farm,
                "id_member" to id_member,
                "name_farm" to name_farm
        )
    }
}