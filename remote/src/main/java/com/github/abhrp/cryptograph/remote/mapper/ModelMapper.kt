package com.github.abhrp.cryptograph.remote.mapper

interface ModelMapper<in M, out E> {
    fun mapToEntity(model: M): E
}