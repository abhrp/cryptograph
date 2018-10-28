package com.github.abhrp.cryptograph.cache.mapper

interface CacheMapper<in P, C, E> {
    fun mapToCache(param: P?, entity: E): C
    fun mapToEntity(cache: C): E
}