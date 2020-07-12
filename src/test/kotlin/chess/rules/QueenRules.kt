package chess.rules

import chess.board.Tile
import chess.pieces.Bishop
import chess.pieces.Piece
import chess.pieces.Queen
import chess.rules.exceptions.EmptyTileException
import chess.rules.exceptions.WrongRuleException

object QueenRules : Rules {

    override fun calculatePossibleAttacks(row: Int, column: Int): List<Tile> {
        val currentTile = BishopRules.board.getTile(row, column) ?: return emptyList()
        val nullablePiece = currentTile.currentPiece
        BishopRules.validatePiece(nullablePiece)
        return RookRules.getPossibleAttacks(currentTile) + BishopRules.getPossibleAttacks(currentTile)
    }

    override fun calculatePossibleMoves(row: Int, column: Int): List<Tile> {
        val currentTile = BishopRules.board.getTile(row, column) ?: return emptyList()
        val nullablePiece = currentTile.currentPiece
        BishopRules.validatePiece(nullablePiece)
        return RookRules.getPossibleMoves(currentTile) + BishopRules.getPossibleMoves(currentTile)
    }

    override fun validatePiece(piece: Piece?) {
        if (piece == null)
            throw EmptyTileException()
        else if ((piece is Queen).not())
            throw WrongRuleException()
    }

}
