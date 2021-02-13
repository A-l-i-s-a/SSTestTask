package com.ivlieva.sstesttask.entyty

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.sql.Timestamp

@Parcelize
data class Task(
    var id: Long = 0L,
    var dateStart: Timestamp? = null,
    var dateFinish: Timestamp? = null,
    var name: String = "",
    var description: String = "",
    var attachments: List<Uri> = listOf(),
    var isNeedSynchronization: Boolean = false
) : Parcelable