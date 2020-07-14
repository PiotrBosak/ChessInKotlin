package chess.board

import Color.*
import chess.board.exceptions.IllegalAttackException
import chess.board.exceptions.IllegalMoveException
import chess.pieces.*
import chess.rules.*
import java.lang.RuntimeException

class Board {
    val tiles: List<Tile> = BoardFactory.createTiles()
    var recentlyMovedPiece: Piece? = null
    fun getTile(row: Int, column: Int) = tiles.firstOrNull { it.row == row && it.column == column }


    //maybe make makeMove and makeAttack return boolean so that if the move is not possible you return false,
    //todo remember to add checking whether the move is legal in terms of mate
    @Throws(IllegalMoveException::class)
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
        movePiecesAttack(startingTile, destinationTile)

    }

    private fun movePiecesAttack(startingTile: Tile, destinationTile: Tile) {
        if (isAttackLePassant(startingTile, destinationTile))
            moveLePassant(startingTile, destinationTile)
        else {
            destinationTile.currentPiece = startingTile.currentPiece
            startingTile.currentPiece = null
            recentlyMovedPiece = destinationTile.currentPiece
            markingKingOrPawnOrRookTheyMoved(destinationTile)
        }
    }

    private fun isAttackLePassant(startingTile: Tile, destinationTile: Tile): Boolean {
        val piece = startingTile.currentPiece ?: throw RuntimeException()
        return if (piece is Pawn && destinationTile.currentPiece == null) {//pretty ugly way of checking if it's le passant
            val tileWithCapturedTile = if (piece.color == WHITE)
                getTile(destinationTile.row - 1, destinationTile.column) ?: throw RuntimeException()
            else getTile(destinationTile.row + 1, destinationTile.column) ?: throw RuntimeException()
            val capturedPiece = tileWithCapturedTile.currentPiece ?: throw RuntimeException()
            capturedPiece is Pawn && capturedPiece === recentlyMovedPiece

        } else false
    }

    private fun moveLePassant(startingTile: Tile, destinationTile: Tile) {
        val pawn = startingTile.currentPiece!! as Pawn
        val capturedPawnTile: Tile = if (pawn.color == WHITE)
            getTile(destinationTile.row - 1, destinationTile.column) ?: throw RuntimeException()
        else
            getTile(destinationTile.row + 1, destinationTile.column) ?: throw RuntimeException()
        destinationTile.currentPiece = startingTile.currentPiece
        startingTile.currentPiece = null
        capturedPawnTile.currentPiece = null


    }

    private fun movePieces(startingTile: Tile, destinationTile: Tile) {
        when {
            isMoveCastling(startingTile, destinationTile) -> doCastling(startingTile, destinationTile)
            isPawnMovingByTwoTile(startingTile, destinationTile) -> makePawnByTwoTiles(startingTile, destinationTile)
            else -> {
                destinationTile.currentPiece = startingTile.currentPiece
                startingTile.currentPiece = null
                markingKingOrPawnOrRookTheyMoved(destinationTile)
            }
        }
        recentlyMovedPiece = destinationTile.currentPiece
    }

    private fun isPawnMovingByTwoTile(startingTile: Tile, destinationTile: Tile): Boolean {
        return kotlin.math.abs(startingTile.row - destinationTile.row) > 1 && startingTile.currentPiece!! is Pawn
    }


    private fun makePawnByTwoTiles(startingTile: Tile, destinationTile: Tile) {
        destinationTile.currentPiece = startingTile.currentPiece
        startingTile.currentPiece = null
        (destinationTile.currentPiece as Pawn).hasJustMovedByTwoTiles = true
    }


    private fun markingKingOrPawnOrRookTheyMoved(destinationTile: Tile) {
        when (val piece = destinationTile.currentPiece!!) {
            is King -> piece.hasMoved = true
            is Rook -> piece.hasMoved = true
            is Pawn -> piece.hasJustMovedByTwoTiles = false
            else -> return
        }

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




