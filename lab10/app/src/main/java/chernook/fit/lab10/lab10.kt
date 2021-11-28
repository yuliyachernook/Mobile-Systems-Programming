package chernook.fit.lab10

import java.util.regex.Pattern
import kotlin.math.sqrt

object StringDemo {
    object StringDemo {
        @JvmStatic
        fun main(args: Array<String>) {

            println("-----------------------------")
            println("1")
            val palindrome = "Dot saw I was Tod"
            val len = palindrome.length
            val tempCharArray = CharArray(len)
            val charArray = CharArray(len)
            for (i in 0 until len) {
                tempCharArray[i] = palindrome[i]
            }
            for (j in 0 until len) {
                charArray[j] = tempCharArray[len - 1 - j]
            }
            val reversePalindrome = String(charArray)
            println(reversePalindrome)

            println("-----------------------------")
            println("2")
            val immutable: String = "Immutable"
            var mutable: Int = 7
            mutable = 77
            val anotherImmutable: Double = 7.0

            val str: String = "777"
            val number: Int = str.toInt()
            println("Immutable value: $immutable")
            println("Enter number or empty string:")
            val fromConsole: Int? = readLine()?.toIntOrNull()
            println("Value from console: $fromConsole")

            println("-----------------------------")
            println("3")
            println(isValid("yuliyachernook@gmail.com", "7777777"))

            val holiday: String? = readLine();
            when (holiday) {
                Holidays.NEW_YEAR.value, Holidays.BIRTHDAY.value, Holidays.WOMENS_DAY.value -> println("Holiday")
                null, "" -> println("Wrong date")
                else -> println("Regular day")
            }

            println(doOperation(7, 13, '+'))
            println(doOperation(5, 9, '/'))
            //println(doOperation(5,9,'^'))

            println("Index of max: " + intArrayOf(1, 2, 3, 4, 5, 9, 6, 7).indexOfMax())
            println("Coincidence count: " + "YuliyaChernook".coincidence("Yulechka"))

            println("-----------------------------")
            println("4")
            println("Factorial 7: " + factorial1(7))
            println("Factorial 77: " + factorial2(77))

            var count = 0
            val primeList: MutableList<Int> = mutableListOf()
            val primeArray = Array(10) { 0 }
            for (x in 2 until 10000) {
                if (isPrime(x)) {
                    count++
                    if (count < 20) {
                        primeList.add(x)
                    } else if ((count > 20) and (count < 30)) {
                        primeArray[count - 21] = x
                    }
                }
            }
            println("Count: " + count)

            println("-----------------------------")
            println("5")
            println(containsIn(primeList))

            val numbers = mutableListOf(1, 2, 3, 4, 5, 5, 7)
            numbers += 7
            numbers.add(7)
            numbers.filter { x->x%2==0 }.forEach{ println(it) }
            println(numbers.filter(::isPrime))
            println(numbers.find { x->x==7 })
            println(numbers.all { x->x>0 })
            println(numbers.any { x->(x>5) and (x<7) })
            println(numbers.groupBy { x-> if (x%2==0) "Even" else "Odd" })
            val (first,second) = numbers
            println("$first;$second")

            var amountOfCorrect = mutableMapOf("Chernook" to 37, "Romanitski" to 35, "Moroz" to 34, "Firsov" to 21)
            amountOfCorrect.forEach{(key,value)->
                val newValue = when(value){
                    40 -> 10
                    39 -> 9
                    38 -> 8
                    in 35..37 -> 7
                    in 32..34 -> 6
                    in 29..31 -> 5
                    in 25..28 -> 4
                    in 22..24 -> 3
                    in 19..21 -> 2
                    in 0..18 -> 1
                    else -> null
                }
                println("Student: $key; Mark: $newValue")
            }
        }

        private fun isValid(login: String, password: String): Boolean {
            fun notNull(login: String?, password: String?): Boolean = !((login == null) and (password == null))
            return notNull(login, password) and Pattern.compile(
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
                        + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
                        + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
            ).matcher(login).matches() and ((password.length > 6) and (password.length < 12))
        }

        enum class Holidays(val value: String) {
            NEW_YEAR("31-12-2021"),
            BIRTHDAY("02-04-2021"),
            WOMENS_DAY("08-03-2021")
        }

        private fun doOperation(a: Int, b: Int, operation: Char): Double {
            return when (operation) {
                '+' -> (a + b).toDouble()
                '-' -> (a - b).toDouble()
                '*' -> (a * b).toDouble()
                '/' -> a.toDouble() / b
                '%' -> a.toDouble() % b
                else -> throw IllegalArgumentException()
            }
        }

        private fun IntArray.indexOfMax(): Int? {
            var maxValue = 0
            var index = 0
            if (this.isEmpty()) {
                return null
            }
            for (i in this.indices) {
                if (this[i] > maxValue) {
                    maxValue = this[i]
                    index = i
                }
            }
            if (this.count { x -> x == maxValue } > 1) {
                return null
            }
            return index
        }

        private fun String.coincidence(str: String): Int {
            var count = 0
            val minLength = if (this.length < str.length) this.length else str.length
            for (i in 0 until minLength) {
                if (this[i] == str[i]) {
                    count++
                }
            }
            return count
        }

        private fun factorial1(n: Int): Double {
            var result: Double = 1.0
            for (x in 1..n) {
                result *= x
            }
            return result
        }


        private fun factorial2(n: Int): Double = if (n < 2) 1.0 else n * factorial2(n - 1)

        private fun isPrime(number: Int): Boolean {
            for (x in 2 until (sqrt(number.toDouble()).toInt()) + 1) {
                if (number % x == 0) {
                    return false
                }
            }
            return true
        }

        private fun containsIn(collection: Collection<Int>): Boolean = collection.any { x -> x == 2 }
    }
}
