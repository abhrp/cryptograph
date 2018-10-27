package com.github.abhrp.cryptograph.data.mapper

interface EntityMapper<E, D> {
    fun mapToDomain(entity: E): D
    fun mapToEntity(domain: D): E
}