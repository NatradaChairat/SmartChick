package com.android.smartchick.data

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Activity (
    var date: String? = null,
    var detail_act: String? = null,
    var id_activity: String? = null,
    var name_act: String? = null
){ }