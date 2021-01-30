package com.ivlieva.sstesttask.repository.remote_data_source.retrofit

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

class TaskNetworkEntity (
    @SerializedName("id")
    @Expose
    var id: Long = 0L,
    @SerializedName("date_start")
    @Expose
    var dateStart: Timestamp? = null,
    @SerializedName("date_finish")
    @Expose
    var dateFinish: Timestamp? = null,
    @SerializedName("name")
    @Expose
    var name: String = "",
    @SerializedName("description")
    @Expose
    var description: String = "",
    @SerializedName("urls")
    @Expose
    var attachmentsUrls: MutableList<String> = mutableListOf()
)

class DataNetworkEntity (
    @SerializedName("task")
    @Expose
    var task: Map<String, TaskNetworkEntity> = HashMap()
)