package chernook.fit.lab11

object ChessBoard{
    var chessPositions = listOf<FigureOnTheDesk>()

    fun addFigure(figure: FigureOnTheDesk){
        chessPositions += figure
    }
}
