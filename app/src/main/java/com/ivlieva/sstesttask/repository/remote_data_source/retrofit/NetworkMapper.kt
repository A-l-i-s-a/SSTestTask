package com.ivlieva.sstesttask.repository.remote_data_source.retrofit

import com.ivlieva.sstesttask.entyty.Task
import com.ivlieva.sstesttask.util.EntityMapper
import com.ivlieva.sstesttask.util.saveFile
import javax.inject.Inject

class NetworkMapper
@Inject
constructor() :
    EntityMapper<TaskNetworkEntity, Task> {

    override fun mapFromEntity(entity: TaskNetworkEntity): Task {
        return Task(
            id = entity.id,
            name = entity.name,
            description = entity.description,
            dateStart = entity.dateStart,
            dateFinish = entity.dateFinish
        )
    }

    override fun mapToEntity(domainModel: Task): TaskNetworkEntity {
        val attachmentsUrls = domainModel.attachments.map { saveFile(it) }
        return TaskNetworkEntity(
            id = domainModel.id,
            name = domainModel.name,
            description = domainModel.description,
            dateStart = domainModel.dateStart,
            dateFinish = domainModel.dateFinish,
            attachmentsUrls = attachmentsUrls as MutableList<String>
        )
    }

    fun mapFromEntityList(entities: List<TaskNetworkEntity>): List<Task> {
        return entities.map { mapFromEntity(it) }
    }

    fun mapFromDataEntity(data: DataNetworkEntity): List<Task> {
        return data.task.values.map { mapFromEntity(it) }
    }

}