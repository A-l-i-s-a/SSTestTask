package com.ivlieva.sstesttask.repository.local_data_source.realm

import com.ivlieva.sstesttask.entyty.Task
import com.ivlieva.sstesttask.util.EntityMapper
import io.realm.RealmList
import java.sql.Timestamp
import javax.inject.Inject

class CacheMapper
@Inject
constructor():
    EntityMapper<TaskCacheEntity, Task> {

    override fun mapFromEntity(entity: TaskCacheEntity): Task {
        return Task(
            id = entity.id,
            name = entity.name,
            description = entity.description,
            dateStart = entity.dateStart?.let { Timestamp(it) },
            dateFinish = entity.dateFinish?.let { Timestamp(it) }
        )
    }

    override fun mapToEntity(domainModel: Task): TaskCacheEntity {
        val map = domainModel.attachments.map { it.path }.toTypedArray()
        return TaskCacheEntity(
            id = domainModel.id,
            name = domainModel.name,
            description = domainModel.description,
            dateStart = domainModel.dateStart?.time,
            dateFinish = domainModel.dateFinish?.time,
            attachmentsPath = RealmList(*map)

        )
    }

    fun mapFromEntityList(entities: List<TaskCacheEntity>): List<Task>{
        return entities.map { mapFromEntity(it) }
    }
}


