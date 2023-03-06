import kotlin.math.sqrt

class Calc {
    fun findRoots(a: Double, b: Double, c: Double, accuracy: Double = 1e-5): DoubleArray {
        validateData(a, b, c, accuracy)
        val d = b * b - 4 * a * c
        //проверка на отрицательный дискриминант
        if (d < -accuracy) {
            return DoubleArray(0)
        }
        val x1 = (-b + sqrt(d)) / (2 * a)
        //проверка на то, что дискриминант не нулевой
        if (d < accuracy) {
            return doubleArrayOf(x1)
        }
        val x2 = (-b - sqrt(d)) / (2 * a)
        return doubleArrayOf(x1, x2)
    }

    private fun validateData(a: Double, b: Double, c: Double, e: Double) {
        if (a < e && a > -e) {
            throw Exception("a не может быть равна нулю")
        }
        if (isForbidden(a) || isForbidden(b) || isForbidden(c)) {
            throw Exception("Недопустимые значения переменных")
        }
    }

    private fun isForbidden(a: Double): Boolean {
        return  a.isNaN() || a.isInfinite()
    }
}