package chess.rules

import chess.board.Board
import chess.board.Tile
import chess.pieces.Piece

interface Rules {
    fun calculatePossibleMoves(row: Int, column: Int,board:Board): List<Tile>
    fun calculatePossibleAttacks(row: Int, column: Int,board:Board): List<Tile>
    fun validatePiece(piece: Piece?): Unit


}
