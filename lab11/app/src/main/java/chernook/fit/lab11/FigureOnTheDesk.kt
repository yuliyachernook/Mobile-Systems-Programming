package chernook.fit.lab11

//Task 1 b,c
class FigureOnTheDesk(_name: String) : ChessFigure {

    companion object{
        var listFigures = mutableListOf<String>()
    }

    var name: String = ""
    var color: String = ""
    var posLetter: String? = ""
    var posNumber: Int? = 0

    //1 d
    init {
        if (_name == "Rook" && listFigures.count{ x->x.equals(_name)} < 4 || _name == "Knight" && listFigures.count{ x->x.equals(_name)} < 4
            || _name == "Bishop" && listFigures.count{ x->x.equals(_name)} < 4 || _name == "Queen" && listFigures.count{ x->x.equals(_name)} < 2
            || _name == "King" && listFigures.count{ x->x.equals(_name)} < 2 || _name == "Pawn" && listFigures.count{ x->x.equals(_name)} < 20
        ) {
            name = _name
            listFigures.add(_name)
        }
    }

    constructor(_name: String, _color: String, _posLetter: String, _posNumber: Int) : this(_name) {
        color = _color
        posLetter = if(_posLetter.length == 1 && _posLetter<"I" && _posLetter >= "A") {
            _posLetter
        } else{
            null
        }
        posNumber = if(_posNumber in 1..8){
            _posNumber
        } else{
            null
        }
    }

    //1 e
    fun RunToPosition(posLet: String, posNum: Int) {
        posLetter = posLet
        posNumber = posNum
        toStepList()
        println("The figure moved to the position $posLetter$posNum")
    }

    var stepList = listOf("")

    //1 f
    fun toStepList() {
        stepList += "${this.name}: $posLetter + $posNumber"
        println("The figure ${this.name} has been added to the movement list")
    }

    //1 g
    class Condition {
        var inBattle: String = ""
        var canBump: String = ""
        var canGo: String = ""
    }

    override fun chessStepToForward() {
        println("The figure steps forward")
    }

}

//1h
fun FigureOnTheDesk.deleteFigure() {
    this.posNumber = null
    this.posLetter = null
    println("The figure ${this.name} has been deleted")
}
