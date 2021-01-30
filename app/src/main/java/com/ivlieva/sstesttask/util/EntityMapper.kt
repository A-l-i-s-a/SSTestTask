package com.ivlieva.sstesttask.util

interface EntityMapper <Entity, DomainModel>{

    fun mapFromEntity(entity: Entity): DomainModel

    fun mapToEntity(domainModel: DomainModel): Entity
}