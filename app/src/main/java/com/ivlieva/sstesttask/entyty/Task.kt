package com.ivlieva.sstesttask.entyty

import android.net.Uri
import java.sql.Timestamp

data class Task(
    var id: Long = 0L,
    var dateStart: Timestamp? = null,
    var dateFinish: Timestamp? = null,
    var name: String = "",
    var description: String = "",
    var attachments: List<Uri> = listOf()
)