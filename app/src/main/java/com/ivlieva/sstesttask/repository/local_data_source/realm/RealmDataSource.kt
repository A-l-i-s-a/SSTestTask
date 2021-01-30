package com.ivlieva.sstesttask.repository.local_data_source.realm

import com.ivlieva.sstesttask.util.setId
import io.realm.Realm
import io.realm.RealmResults
import javax.inject.Inject


class RealmDataSource @Inject constructor() {
    fun getTask(id: String): TaskCacheEntity {
        return Realm.getDefaultInstance()
            .where<TaskCacheEntity>(TaskCacheEntity::class.java)
            .equalTo("id", id)
            .findFirst()!!
    }

    fun getTaskByDate(date: Long): List<TaskCacheEntity> {
        return Realm.getDefaultInstance()
            .where<TaskCacheEntity>(TaskCacheEntity::class.java)
            .between("dateStart", date, date + 24 * 60 * 60 * 1000)
            .findAll()
    }

    fun getTasks(): List<TaskCacheEntity> {
        return Realm.getDefaultInstance()
            .where<TaskCacheEntity>(TaskCacheEntity::class.java)
            .findAll()
    }

    fun saveTask(task: TaskCacheEntity) {
        val realm = Realm.getDefaultInstance()
        realm.executeTransaction {
            create(task, it)
        }
        realm.close()
    }

    private fun create(
        task: TaskCacheEntity,
        it: Realm
    ) {
        val createObject = it.createObject(TaskCacheEntity::class.java, setId())
        createObject.name = task.name
        createObject.dateStart = task.dateStart
        createObject.dateFinish = task.dateFinish
        createObject.description = task.description
    }

    fun saveTasks(tasks: List<TaskCacheEntity>) {
        val realm = Realm.getDefaultInstance()
        realm.executeTransaction {
            for (task in tasks) {
                create(task, it)
            }
        }
        realm.close()
    }

    fun deleteTask(task: TaskCacheEntity) {
        Realm.getDefaultInstance().executeTransaction { realm ->
            val result: RealmResults<TaskCacheEntity> =
                realm.where(TaskCacheEntity::class.java).equalTo("id", task.id).findAll()
            result.deleteAllFromRealm()
        }
    }

    fun deleteAllTasks() {
        Realm.getDefaultInstance().executeTransaction { realm ->
            val result: RealmResults<TaskCacheEntity> =
                realm.where(TaskCacheEntity::class.java).findAll()
            result.deleteAllFromRealm()
        }
    }
}