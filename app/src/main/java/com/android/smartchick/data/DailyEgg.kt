package com.android.smartchick.data

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import java.util.*

@IgnoreExtraProperties
data class DailyEgg(
        var id_daily: String? = null,
        var id_chick: String? = null,
        var date: String? = null,
        var amount_egg: Int? = null,
        var quality_egg: String? =null
) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
                "id_daily" to id_daily,
                "id_chick" to id_chick,
                "date" to date,
                "amount_egg" to amount_egg,
                "quality_egg" to quality_egg
        )
    }
}