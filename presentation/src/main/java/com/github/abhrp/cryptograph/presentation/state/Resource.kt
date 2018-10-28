package com.github.abhrp.cryptograph.presentation.state

class Resource<out T> constructor(val status: ResourceState, val data: T?, val error: String?)