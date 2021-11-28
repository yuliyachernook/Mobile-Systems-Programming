package chernook.fit.lab11;

interface ChessFigure{
    var unit:String
    get() = this.toString()
    set(value){
        unit = value
    }

    fun chessStepToForward()
    fun chessStepToBack() = println("The figure steps back")
}