package chess.board

import Color
import chess.pieces.Piece

class Tile(color: Color, column: Int, row: Int, val startingPiece: Piece?) {
    val row = validateRow(row)
    val column = validateColumn(column)
    val color: Color = validateColor(color, row, column)
    private fun validateColumn(column: Int): Int {
        return if (column in 1..8) column else throw IllegalTileException()
    }


    private fun validateColor(color: Color, row: Int, column: Int): Color {
        if (isColorValid(row, column, color))
            return color
        else throw IllegalTileException()
    }

    private fun isColorValid(row: Int, column: Int, color: Color): Boolean {
        return if ((row + column) % 2 == 0)
            color == Color.BLACK
        else color == Color.WHITE
    }

    private fun validateRow(row: Int): Int {
        return if (row in 1..8) row else throw IllegalTileException()
    }

    val currentPiece: Piece? = startingPiece


}