package chess.board

import Color
import chess.board.exceptions.IllegalTileException
import chess.pieces.Piece

class Tile(color: Color, column: Int, row: Int, val startingPiece: Piece?) {
    var currentPiece = startingPiece
    val row = validateRow(row)
    val column = validateColumn(column)
    val color: Color = validateColor(color, row, column)


    private fun validateColor(color: Color, row: Int, column: Int): Color {
        if (isColorValid(row, column, color))
            return color
        else throw IllegalTileException
    }

    private fun isColorValid(row: Int, column: Int, color: Color): Boolean {
        return if ((row + column) % 2 == 0)
            color == Color.BLACK
        else color == Color.WHITE
    }

    private fun validateRow(row: Int): Int {
        return if (row in 1..8) row else throw IllegalTileException
    }

    private fun validateColumn(column: Int): Int {
        return if (column in 1..8) column else throw IllegalTileException
    }

    fun isEmpty(): Boolean = currentPiece == null
    fun hasPiece(): Boolean = currentPiece != null
    fun hasStartingPiece() = currentPiece === startingPiece

    companion object {

        fun isRowAndColumnValid(row: Int, column: Int): Boolean = (row in 1..8 && column in 1..8).not()

    }

}