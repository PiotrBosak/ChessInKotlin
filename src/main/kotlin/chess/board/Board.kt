package chess.board

import chess.board.exceptions.IllegalAttackException
import chess.board.exceptions.IllegalMoveException
import chess.pieces.*
import chess.rules.*
import java.lang.RuntimeException

class Board {
    val tiles: List<Tile> = BoardFactory.createTiles()
    fun getTile(row: Int, column: Int) = tiles.firstOrNull { it.row == row && it.column == column }


    //maybe make makeMove and makeAttack return boolean so that if the move is not possible you return false,
    //todo remember to add checking whether the move is legal in terms of mate
    fun makeMoveWithoutCheckingMate(startingTile: Tile, destinationTile: Tile) {//for testing purposes of valid moves
        val moves = getAppropriateMoves(startingTile)
        if (moves.contains(destinationTile).not())
            throw IllegalMoveException
        movePieces(startingTile, destinationTile)
    }

    fun makeAttack(startingTile: Tile, destinationTile: Tile) {
        val attacks = getAppropriateAttacks(startingTile)
        if (attacks.contains(destinationTile).not())
            throw IllegalAttackException
        destinationTile.currentPiece = startingTile.currentPiece
        startingTile.currentPiece = null
        markKingOrRookTheyMoved(destinationTile)

    }

    private fun movePieces(startingTile: Tile, destinationTile: Tile) {
        if (isMoveCastling(startingTile, destinationTile))
            doCastling(startingTile, destinationTile)
        else {
            destinationTile.currentPiece = startingTile.currentPiece
            startingTile.currentPiece = null
            markKingOrRookTheyMoved(destinationTile)
        }
    }


    private fun markKingOrRookTheyMoved(destinationTile: Tile) {
        val piece = destinationTile.currentPiece!!
        if (piece is King)
            piece.hasMoved = true
        else if (piece is Rook)
            piece.hasMoved

    }

    private fun isMoveCastling(startingTile: Tile, destinationTile: Tile): Boolean {
        val piece = startingTile.currentPiece ?: throw RuntimeException()
        return piece is King && kotlin.math.abs(startingTile.column - destinationTile.column) > 1//meaning the king is moving more than 1 tile

    }

    private fun doCastling(startingTile: Tile, destinationTile: Tile) {
        val king = startingTile.currentPiece as King
        king.hasMoved = true
        val rookTile = getRookTile(destinationTile)
        val rook = rookTile.currentPiece as Rook
        rook.hasMoved = true
        destinationTile.currentPiece = king
        startingTile.currentPiece = null
        val rookDest = getRookNewTile(destinationTile)
        rookDest.currentPiece = rook
        rookTile.currentPiece = null
    }


    private fun getRookTile(destinationTile: Tile): Tile {
        return if (destinationTile.row == 1) {
            if (destinationTile.column == 3)
                getTile(1, 1) ?: throw RuntimeException()
            else getTile(1, 8) ?: throw RuntimeException()
        } else {
            if (destinationTile.column == 3)
                getTile(8, 1) ?: throw RuntimeException()
            else getTile(8, 8) ?: throw RuntimeException()
        }
    }

    private fun getRookNewTile(kingTile: Tile): Tile {
        return if (kingTile.row == 1) {
            if (kingTile.column == 3)
                getTile(1, 4) ?: throw RuntimeException()
            else getTile(1, 6) ?: throw RuntimeException()
        } else {
            if (kingTile.column == 3)
                getTile(8, 4) ?: throw RuntimeException()
            else getTile(8, 6) ?: throw RuntimeException()
        }
    }


    private fun getAppropriateMoves(tile: Tile): List<Tile> {
        //check for castling
        val piece = tile.currentPiece ?: throw RuntimeException("you try to move empty tile, shouldn't happen")
        return when (piece) {
            is Pawn -> PawnRules.calculatePossibleMoves(tile.row, tile.column, this)
            is Rook -> RookRules.calculatePossibleMoves(tile.row, tile.column, this)
            is Bishop -> BishopRules.calculatePossibleMoves(tile.row, tile.column, this)
            is Queen -> QueenRules.calculatePossibleMoves(tile.row, tile.column, this)
            is Knight -> KnightRules.calculatePossibleMoves(tile.row, tile.column, this)
            is King -> KingRules.calculatePossibleMoves(tile.row, tile.column, this)
        }
    }

    private fun getAppropriateAttacks(tile: Tile): List<Tile> {
        val piece = tile.currentPiece ?: throw RuntimeException("you try to move empty tile, shouldn't happen")
        return when (piece) {
            is Pawn -> PawnRules.calculatePossibleAttacks(tile.row, tile.column, this)
            is Rook -> RookRules.calculatePossibleAttacks(tile.row, tile.column, this)
            is Bishop -> BishopRules.calculatePossibleAttacks(tile.row, tile.column, this)
            is Queen -> QueenRules.calculatePossibleAttacks(tile.row, tile.column, this)
            is Knight -> KnightRules.calculatePossibleAttacks(tile.row, tile.column, this)
            is King -> KingRules.calculatePossibleAttacks(tile.row, tile.column, this)
        }
    }
}




