package com.github.abhrp.cryptograph.presentation.mapper

interface ViewMapper<V, D> {
    fun mapToView(domain: D): V
    fun mapToDomain(view: V): D
}