package chernook.fit.lab11

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val player = Player("Yuliya", 1, 7, 2)
        println(player)

        val queen = FigureOnTheDesk("Queen", "White", "A", 2)
        queen.RunToPosition("A", 4)

        val queenCondition = FigureOnTheDesk.Condition()
        queenCondition.inBattle = "No"
        queenCondition.canBump = "Yes"
        queenCondition.canGo = "Yes"
        ChessBoard.addFigure(queen)

        val knight = FigureOnTheDesk("Knight", "Black", "B", 5)
        knight.RunToPosition("C", 7)

        val knightCondition = FigureOnTheDesk.Condition()
        knightCondition.inBattle = "Yes"
        knightCondition.canBump = "No"
        knightCondition.canGo = "No"
        ChessBoard.addFigure(knight)

        for (i in ChessBoard.chessPositions) {
            println("Figures on the desk: ${i.name} ${i.color} ${i.posLetter}${i.posNumber}")
        }

        queen.deleteFigure()
        knight.deleteFigure()

        val a = A(10)
        a.display()

        val resultSum = a.converter("+")?.invoke(2.3, 5.5)
        val resultSub = a.converter("-")?.invoke(2.3, 5.5)
        val resultMult = a.converter("*")?.invoke(2.3, 5.5)
        val resultDiv = a.converter("/")?.invoke(2.3, 5.5)
        val resultNull = a.converter(null)?.invoke(2.3, 5.5)
        println("\nSum: $resultSum")
        println("Sub: $resultSub")
        println("Mult: $resultMult")
        println("Div: $resultDiv")
        println("Null: $resultNull")

    }
}