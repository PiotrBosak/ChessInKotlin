package chess.board

import chess.pieces.*
import chess.rules.*
import java.lang.RuntimeException

class Board {
    val tiles: List<Tile> = BoardFactory.createTiles()
    fun getTile(row: Int, column: Int) = tiles.firstOrNull { it.row == row && it.column == column }


    fun makeMove(startingTile: Tile, destinationTile: Tile) {
        val moves = getAppropriateMoves(startingTile)
        if (moves.contains(destinationTile).not())
            throw IllegalMoveException
        destinationTile.currentPiece = startingTile.currentPiece
        startingTile.currentPiece = null
    }

    fun makeAttack(startingTile: Tile, destinationTile: Tile) {
        val attacks = getAppropriateAttacks(startingTile)
        if (attacks.contains(destinationTile).not())
            throw IllegalAttackException
        destinationTile.currentPiece = startingTile.currentPiece
        startingTile.currentPiece = null
    }

    private fun getAppropriateMoves(tile: Tile): List<Tile> {
        return when (tile.currentPiece) {
            is Pawn -> PawnRules.calculatePossibleMoves(tile.row, tile.column,this)
            is Rook -> RookRules.calculatePossibleMoves(tile.row, tile.column,this)
            is Bishop -> BishopRules.calculatePossibleMoves(tile.row, tile.column,this)
            is Queen -> QueenRules.calculatePossibleMoves(tile.row, tile.column,this)
            else -> throw RuntimeException("not finished yet")
        }
    }

    private fun getAppropriateAttacks(tile: Tile): List<Tile> {
        return when (tile.currentPiece) {
            is Pawn -> PawnRules.calculatePossibleAttacks(tile.row, tile.column,this)
            is Rook -> RookRules.calculatePossibleAttacks(tile.row, tile.column,this)
            is Bishop -> BishopRules.calculatePossibleAttacks(tile.row, tile.column,this)
            is Queen -> QueenRules.calculatePossibleAttacks(tile.row, tile.column,this)
            else -> throw RuntimeException("not finished yet")
        }
    }
}



