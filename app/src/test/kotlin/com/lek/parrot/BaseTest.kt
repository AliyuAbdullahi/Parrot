package com.lek.parrot

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.BeforeEach

@ExperimentalCoroutinesApi
abstract class BaseTest {
    @ExperimentalCoroutinesApi
    val dispatcher = TestCoroutineDispatcher()

    @BeforeEach
    open fun setUp() {
        Dispatchers.setMain(dispatcher)
    }
}