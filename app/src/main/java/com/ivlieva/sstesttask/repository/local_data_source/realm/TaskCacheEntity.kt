package com.ivlieva.sstesttask.repository.local_data_source.realm

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class TaskCacheEntity(
    @PrimaryKey
    var id: Long = 0L,
    var dateStart: Long? = null,
    var dateFinish: Long? = null,
    var name: String = "",
    var description: String = "",
    var attachmentsPath: RealmList<String?> = RealmList(),
    var isNeedSynchronization: Boolean = false
) : RealmObject()