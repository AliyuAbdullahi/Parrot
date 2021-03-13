package com.lek.parrot.core

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

abstract class Interactor<Params, ReturnType>(
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) {
    protected abstract fun run(params: Params): Flow<ReturnType>

    operator fun invoke(params: Params): Flow<ReturnType> {
        return run(params).flowOn(dispatcher)
    }
}

operator fun <ReturnType> Interactor<Unit, ReturnType>.invoke(): Flow<ReturnType> = this(Unit)