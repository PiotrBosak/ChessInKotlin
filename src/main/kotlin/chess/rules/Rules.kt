package chess.rules

import chess.board.Board
import chess.board.Tile
import chess.pieces.Piece

interface Rules {
    val board: Board
        get() = Board()

    fun calculatePossibleMoves(row: Int, column: Int): List<Tile>
    fun calculatePossibleAttacks(row: Int, column: Int): List<Tile>
    fun validatePiece(piece: Piece?): Unit


}
