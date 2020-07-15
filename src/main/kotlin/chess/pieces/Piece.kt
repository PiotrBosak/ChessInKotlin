package chess.pieces

import Color

sealed class Piece(val color: Color)
class Pawn(color: Color) : Piece(color) {
    var hasJustMovedByTwoTiles = false
}

class Rook(color: Color) : Piece(color) {
    var hasMoved = false
}

class Knight(color: Color) : Piece(color)
class Bishop(color: Color) : Piece(color)
class Queen(color: Color) : Piece(color)
class King(color: Color) : Piece(color) {
    var hasMoved = false
    var isChecked = false
    var isMated = false
}

