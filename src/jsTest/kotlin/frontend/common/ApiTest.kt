package frontend.common

import com.varabyte.truthish.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

val testScope = MainScope()


class ApiTest {



    @Test
    fun testTruthisAsserts() {
        assertThat(2+2).isEqualTo(4)
    }

    @Test
    fun testTruthisAssertsFails() {
        assertThat(2+2).isEqualTo(3)
    }

    suspend fun doubleWithDelay(value: Int) : Int {
        delay(1000L)
        return value*2
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testKotlinxCoroutineTestWithTruthish() = runTest {
        val result = doubleWithDelay(2)
        assertThat(result).isEqualTo(4)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testKotlinxCoroutineTestWithTruthishFails() = runTest {
        val result = doubleWithDelay(2)
        assertThat(result).isEqualTo(3)
    }

    // This is the closest I've been to an integration test
    // it uses kotlinx-coroutines-test and truthish but
    // it doesn't get anything
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testGetSingleResult() = runTest {
        val TESTED_ID = "ES.GFA.WMS"

        val result = getSingleResult(TESTED_ID)
        assertThat(result.ID).isEqualTo(TESTED_ID)
    }
}
