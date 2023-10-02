package com.pulsepreassurenotes.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Record(
    var lowPressure: Int? = 80,
    var hightPressue: Int? = 120,
    var pulse: Int? = 60,
    var year: Int? = 2023,
    var month: String? = "январь",
    var date: Int? = 1,
    var hours: Int? = 0,
    var minutes: Int? = 0
) : Parcelable