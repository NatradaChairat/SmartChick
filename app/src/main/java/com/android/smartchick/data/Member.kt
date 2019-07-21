package com.android.smartchick.data

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Member(
        var id_member: String? = null,
        var email_member: String? = null,
        var password_member: String? = null,
        var name_member: String? = null,
        var address: String? = null,
        var telephone: String? = null,
        var name_farm: String? = null
) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
                "id_member" to id_member,
                "email_member" to email_member,
                "password_member" to password_member,
                "name_member" to name_member,
                "address" to address,
                "telephone" to telephone,
                "name_farm" to name_farm
        )
    }
}