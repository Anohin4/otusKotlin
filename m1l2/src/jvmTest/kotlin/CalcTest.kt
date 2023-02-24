import org.junit.jupiter.api.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource
import java.util.stream.Stream
import kotlin.math.abs
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class MainTest {
    private val calc = Calc()

    @ParameterizedTest
    @ValueSource(doubles = [0.0, Companion.TOO_SMALL_NUMBER])
    @DisplayName(" Нет корней для случая, когда дискриминант отрицательный ")
    fun `no roots case`(number: Double) {
        val result: DoubleArray = calc.findRoots(1.0, number, 1.0)
        assertEquals(0, result.size, "При отрицательном дискриминанте корней быть не должно")
    }

    @Test
    fun `normal case`() {
        val result: DoubleArray = calc.findRoots(1.0, 0.0, -1.0)
        assertEquals(2, result.size, "В общем случае должно быть два корня")
        assertEquals(1.0, abs(result[0]))
        assertEquals(1.0, abs(result[1]))
    }

    @ParameterizedTest
    @ValueSource(doubles = [2.0, 2.00000001])
    fun `one root case`(b: Double) {
        val result: DoubleArray = calc.findRoots(1.0, b, 1.0)
        assertEquals(1, result.size, "Когда дискриминант равен нулю, корень всегда один")
    }

    @ParameterizedTest
    @MethodSource("provideParameters")
    fun `test incorrect params`(a: Double, b: Double, c: Double) {
        assertThrows<Exception> { calc.findRoots(a, b, c) }
    }

    private fun provideParameters(): Stream<Arguments> {
        return Stream.of(
            Arguments.of(Double.NaN, 2.0, 1.0),
            Arguments.of(1.0, Double.NEGATIVE_INFINITY, 1),
            Arguments.of(1.0, 2.0, Double.POSITIVE_INFINITY),
            Arguments.of(0.0, 2.0, 1.0),
            Arguments.of(8e-10, 2.0, 1.0)
        )
    }

    companion object {
        private const val TOO_SMALL_NUMBER = 8e-10
    }
}