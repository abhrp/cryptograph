package com.github.abhrp.cryptograph.ui.mapper

interface UIMapper<in V, out U> {
    fun mapToUiModel(view: V): U
}