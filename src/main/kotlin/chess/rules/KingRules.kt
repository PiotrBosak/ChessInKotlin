package chess.rules

import Color.*
import chess.board.Board
import chess.board.Tile
import chess.pieces.King
import chess.pieces.Piece
import chess.pieces.Rook
import chess.rules.exceptions.EmptyTileException
import chess.rules.exceptions.WrongRuleException
import java.lang.RuntimeException

object KingRules : Rules {
    override fun calculatePossibleMoves(row: Int, column: Int, board: Board): List<Tile> {
        val currentTile = board.getTile(row, column) ?: return emptyList()
        validatePiece(currentTile.currentPiece)
        return getPossibleMoves(currentTile, board) + getCastlingMoves(currentTile, board)
    }

    override fun calculatePossibleAttacks(row: Int, column: Int, board: Board): List<Tile> {
        val currentTile = board.getTile(row, column) ?: return emptyList()
        validatePiece(currentTile.currentPiece)
        return getPossibleAttacks(currentTile, board)
    }

    override fun validatePiece(piece: Piece?) {
        if (piece == null)
            throw EmptyTileException()
        else if ((piece is King).not())
            throw WrongRuleException()
    }

    private fun getPossibleMoves(tile: Tile, board: Board): List<Tile> {
        val mutableList = mutableListOf<Tile>()
        for (row in -1..1)
            for (column in -1..1)
                if (row != 0 || column != 0)
                    if (isPossibleForMove(tile, row, column, board)) {
                        val correctTile = board.getTile(tile.row + row, tile.column + column)
                                ?: throw RuntimeException()
                        mutableList.add(correctTile)
                    }

        return mutableList
    }


    private fun getPossibleAttacks(tile: Tile, board: Board): List<Tile> {
        val mutableList = mutableListOf<Tile>()
        for (row in -1..1)
            for (column in -1..1)
                if (row != 0 || column != 0)
                    if (isPossibleForAttack(tile, row, column, board)) {
                        val correctTile = board.getTile(tile.row + row, tile.column + column)
                                ?: throw RuntimeException()
                        mutableList.add(correctTile)
                    }
        return mutableList
    }

    private fun isPossibleForMove(tile: Tile, row: Int, column: Int, board: Board): Boolean {
        val possibleTile = board.getTile(tile.row + row, tile.column + column)
        return possibleTile != null && possibleTile.isEmpty()
    }

    private fun isPossibleForAttack(tile: Tile, row: Int, column: Int, board: Board): Boolean {
        val possibleTile = board.getTile(tile.row + row, tile.column + column)
        return if (possibleTile != null && possibleTile.hasPiece())
            possibleTile.currentPiece!!.color != tile.currentPiece!!.color
        else false
    }

    private fun getCastlingMoves(tile: Tile, board: Board): List<Tile> {
        val king = tile.currentPiece ?: throw RuntimeException()
        return if (king.color == WHITE)
            whiteCastlings(tile, board)
        else blackCastlings(tile, board)
    }

    private fun whiteCastlings(tile: Tile, board: Board): List<Tile> {
        val firstCastling = getWhiteLeftCastling(tile, board)
        val secondCastling = getWhiteRightCastling(tile, board)
        return listOfNotNull(firstCastling, secondCastling)
    }


    private fun blackCastlings(tile: Tile, board: Board): List<Tile> {
        val firstCastling = getBlackLeftCastling(tile, board)
        val secondCastling = getBlackRightCastling(tile, board)
        return listOfNotNull(firstCastling, secondCastling)
    }

    private fun getWhiteLeftCastling(tile: Tile, board: Board): Tile? {
        val king = tile.currentPiece as King
        if (king.hasMoved)
            return null
        val isQueenGone = board.getTile(1, 4)?.isEmpty() ?: false
        val isBishopGone = board.getTile(1, 3)?.isEmpty() ?: false
        val isKnightGone = board.getTile(1, 2)?.isEmpty() ?: false
        val rookTile = board.getTile(1, 1) ?: return null
        val rook = rookTile.currentPiece ?: return null
        val isRookAvailableForCastling = rook is Rook && rook.hasMoved.not()
        if (isQueenGone && isBishopGone && isKnightGone && isRookAvailableForCastling)
            return board.getTile(1, 3)
        return null


    }

    private fun getWhiteRightCastling(tile: Tile, board: Board): Tile? {
        val king = tile.currentPiece as King
        if (king.hasMoved)
            return null
        val isBishopGone = board.getTile(1, 6)?.isEmpty() ?: false
        val isKnightGone = board.getTile(1, 7)?.isEmpty() ?: false
        val rookTile = board.getTile(1, 8) ?: return null
        val rook = rookTile.currentPiece ?: return null
        val isRookAvailableForCastling = rook is Rook && rook.hasMoved.not()
        if (isBishopGone && isKnightGone && isRookAvailableForCastling)
            return board.getTile(1, 7)
        return null
    }

    private fun getBlackLeftCastling(tile: Tile, board: Board): Tile? {
        val king = tile.currentPiece as King
        if (king.hasMoved)
            return null
        val isQueenGone = board.getTile(8, 4)?.isEmpty() ?: false
        val isBishopGone = board.getTile(8, 3)?.isEmpty() ?: false
        val isKnightGone = board.getTile(8, 2)?.isEmpty() ?: false
        val rookTile = board.getTile(8, 1) ?: return null
        val rook = rookTile.currentPiece ?: return null
        val isRookAvailableForCastling = rook is Rook && rook.hasMoved.not()
        if (isQueenGone && isBishopGone && isKnightGone && isRookAvailableForCastling)
            return board.getTile(8, 3)
        return null
    }

    private fun getBlackRightCastling(tile: Tile, board: Board): Tile? {
        val king = tile.currentPiece as King
        if (king.hasMoved)
            return null
        val isBishopGone = board.getTile(8, 6)?.isEmpty() ?: false
        val isKnightGone = board.getTile(8, 7)?.isEmpty() ?: false
        val rookTile = board.getTile(8, 8) ?: return null
        val rook = rookTile.currentPiece ?: return null
        val isRookAvailableForCastling = rook is Rook && rook.hasMoved.not()
        if (isBishopGone && isKnightGone && isRookAvailableForCastling)
            return board.getTile(8, 7)
        return null
    }


}

