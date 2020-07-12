package chess.rules

import chess.board.Board
import chess.board.Tile
import chess.pieces.Bishop
import chess.pieces.Piece
import chess.pieces.Queen
import chess.rules.exceptions.EmptyTileException
import chess.rules.exceptions.WrongRuleException

object QueenRules : Rules {

    override fun calculatePossibleAttacks(row: Int, column: Int,board: Board): List<Tile> {
        val currentTile = board.getTile(row, column) ?: return emptyList()
        val nullablePiece = currentTile.currentPiece
        validatePiece(nullablePiece)
        return RookRules.getPossibleAttacks(currentTile,board) + BishopRules.getPossibleAttacks(currentTile,board)
    }

    override fun calculatePossibleMoves(row: Int, column: Int,board:Board): List<Tile> {
        val currentTile = board.getTile(row, column) ?: return emptyList()
        val nullablePiece = currentTile.currentPiece
        validatePiece(nullablePiece)
        return RookRules.getPossibleMoves(currentTile,board) + BishopRules.getPossibleMoves(currentTile,board)
    }

    override fun validatePiece(piece: Piece?) {
        if (piece == null)
            throw EmptyTileException()
        else if ((piece is Queen).not())
            throw WrongRuleException()
    }

}
