package chernook.fit.lab11

class A(_x: Int) {
    var x = _x

    private lateinit var prop: String
    fun setUp() {
        prop = "100 + 200"
    }

    fun display() {
        if (::prop.isInitialized) {
            println(prop)
        }
    }

    //3 b)
    override operator fun equals(other: Any?): Boolean {
        return true
    }

    fun compareTo(other: A): Int {
        return compareValuesBy(this, other, A::prop)
    }

    //3 c)
    fun converter(str: String?): ((Double, Double) -> Double)? {
        if (str == null) {
            return null
        }

        when {
            str.contains("+") -> {
                return { firstNumber: Double, secondNumber: Double -> firstNumber + secondNumber }
            }
            str.contains("*") -> {
                return { firstNumber: Double, secondNumber: Double -> firstNumber * secondNumber }
            }
            str.contains("-") -> {
                return { firstNumber: Double, secondNumber: Double -> firstNumber - secondNumber }
            }
            str.contains("/") -> {
                return { firstNumber: Double, secondNumber: Double -> firstNumber / secondNumber }
            }
            else -> return null
        }
    }
}


