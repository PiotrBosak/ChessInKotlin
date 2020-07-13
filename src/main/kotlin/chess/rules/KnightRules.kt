package chess.rules

import chess.board.Board
import chess.board.Tile
import chess.pieces.Knight
import chess.pieces.Piece
import chess.rules.exceptions.EmptyTileException
import chess.rules.exceptions.WrongRuleException
import java.lang.RuntimeException
import kotlin.math.abs

object KnightRules : Rules {
    override fun calculatePossibleMoves(row: Int, column: Int, board: Board): List<Tile> {
        val currentTile = board.getTile(row, column) ?: return emptyList()
        val nullablePiece = currentTile.currentPiece
        validatePiece(nullablePiece)
        return getPossibleMoves(currentTile, board)
    }

    override fun calculatePossibleAttacks(row: Int, column: Int, board: Board): List<Tile> {
        val currentTile = board.getTile(row, column) ?: return emptyList()
        val nullablePiece = currentTile.currentPiece
        validatePiece(nullablePiece)
        return getPossibleAttacks(currentTile, board)
    }

    override fun validatePiece(piece: Piece?) {
        if (piece == null)
            throw EmptyTileException()
        else if ((piece is Knight).not())
            throw WrongRuleException()
    }

    private fun getPossibleMoves(tile: Tile, board: Board): List<Tile> {
        val mutableList = mutableListOf<Tile>()
        for (row in -2..2)
            for (column in -2..2)
                if (row != 0 && column != 0 && abs(row) != abs(column))
                    if (isTileAvailableForMove(tile, row, column, board)) {
                        val correctTile = board.getTile(tile.row + row, tile.column + column)
                                ?: throw RuntimeException()
                        mutableList.add(correctTile)
                    }
        return mutableList
    }

    private fun getPossibleAttacks(tile: Tile, board: Board): List<Tile> {
        val mutableList = mutableListOf<Tile>()
        for (row in -2..2)
            for (column in -2..2)
                if (row != 0 && column != 0 && abs(row) != abs(column))
                    if (isTileAvailableForAttack(tile, row, column, board)) {
                        val correctTile = board.getTile(tile.row + row, tile.column + column)
                                ?: throw RuntimeException()
                        mutableList.add(correctTile)
                    }
        return mutableList
    }


    private fun isTileAvailableForMove(tile: Tile, row: Int, column: Int, board: Board): Boolean {
        val possibleTile = board.getTile(tile.row + row, tile.column + column)
        return possibleTile != null && possibleTile.isEmpty()
    }

    private fun isTileAvailableForAttack(tile: Tile, row: Int, column: Int, board: Board): Boolean {
        val possibleTile = board.getTile(tile.row + row, tile.column + column)
        return if (possibleTile != null && possibleTile.hasPiece())
            possibleTile.currentPiece!!.color != tile.currentPiece!!.color
        else false
    }
}

