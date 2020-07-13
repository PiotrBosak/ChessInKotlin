package chess.rules

import chess.board.Board
import chess.board.Tile
import chess.pieces.King
import chess.pieces.Piece
import chess.rules.exceptions.EmptyTileException
import chess.rules.exceptions.WrongRuleException

object KingRules : Rules {
    override fun calculatePossibleMoves(row: Int, column: Int, board: Board): List<Tile> {
        val currentTile = board.getTile(row, column) ?: return emptyList()
        validatePiece(currentTile.currentPiece)
        return getPossibleMoves(currentTile, board)
    }

    override fun calculatePossibleAttacks(row: Int, column: Int, board: Board): List<Tile> {
        TODO("Not yet implemented")
    }

    override fun validatePiece(piece: Piece?) {
        if (piece == null)
            throw EmptyTileException()
        else if ((piece is King).not())
            throw WrongRuleException()
    }

    private fun getPossibleMoves(currentTile: Tile, board: Board):List<Tile>{
        return emptyList()
    }
}