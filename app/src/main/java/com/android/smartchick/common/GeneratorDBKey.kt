package com.android.smartchick.common

import android.os.Build
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class GeneratorDBKey {
    fun getKey(): String{
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val localDateTime = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("ddMMyyyyHHmmssSSS")
            formatter.format(localDateTime)

        } else {
            val localDateTime = Calendar.getInstance().time
            val formatter = SimpleDateFormat("ddMMyyyyHHmmssSSS")
            formatter.format(localDateTime)

        }
    }
}