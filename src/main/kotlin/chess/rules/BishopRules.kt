package chess.rules

import chess.board.Board
import chess.board.Tile
import chess.pieces.Bishop
import chess.pieces.Piece
import chess.rules.exceptions.EmptyTileException
import chess.rules.exceptions.WrongRuleException

object BishopRules : Rules {

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
        else if ((piece is Bishop).not())
            throw WrongRuleException()
    }

    fun getPossibleMoves(tile: Tile, board: Board): List<Tile> {
        return topRightPositionsMoves(tile, board) + topLeftPositionsMoves(tile, board) +
                downLeftPositionsMoves(tile, board) + downRightPositionsMoves(tile, board)
    }

    fun getPossibleAttacks(tile: Tile, board: Board): List<Tile> {
        return mutableListOf(topLeftPositionsAttacks(tile, board), topRightPositionsAttacks(tile, board),
                downLeftPositionsAttacks(tile, board), downRightPositionsAttacks(tile, board)).filterNotNull()
    }

    private fun topLeftPositionsAttacks(tile: Tile, board: Board): Tile? {
        for (i in (1..8)) {
            val currentTile = board.getTile(tile.row + i, tile.column - i)
            if (currentTile == null)
                break
            else if (currentTile.hasPiece()) {
                if (currentTile.currentPiece!!.color != tile.currentPiece!!.color)
                    return currentTile

            }
        }
        return null
    }

    private fun topRightPositionsAttacks(tile: Tile, board: Board): Tile? {
        for (i in (1..8)) {
            val currentTile = board.getTile(tile.row + i, tile.column + i)
            if (currentTile == null)
                break
            else if (currentTile.hasPiece()) {
                if (currentTile.currentPiece!!.color != tile.currentPiece!!.color)
                    return currentTile

            }
        }
        return null

    }

    private fun downLeftPositionsAttacks(tile: Tile, board: Board): Tile? {
        for (i in (1..8)) {
            val currentTile = board.getTile(tile.row - i, tile.column - i)
            if (currentTile == null)
                break
            else if (currentTile.hasPiece()) {
                if (currentTile.currentPiece!!.color != tile.currentPiece!!.color)
                    return currentTile

            }
        }
        return null

    }

    private fun downRightPositionsAttacks(tile: Tile, board: Board): Tile? {
        for (i in (1..8)) {
            val currentTile = board.getTile(tile.row - i, tile.column + i)
            if (currentTile == null)
                break
            else if (currentTile.hasPiece()) {
                if (currentTile.currentPiece!!.color != tile.currentPiece!!.color)
                    return currentTile

            }
        }
        return null

    }


    private fun topLeftPositionsMoves(tile: Tile, board: Board): List<Tile> {
        val list = mutableListOf<Tile>()
        for (i in (1..8)) {
            val currentTile = board.getTile(tile.row + i, tile.column - i)
            if (currentTile == null || currentTile.hasPiece())
                break
            else if (currentTile.isEmpty())
                list.add(currentTile)
        }
        return list

    }

    private fun topRightPositionsMoves(tile: Tile, board: Board): List<Tile> {
        val list = mutableListOf<Tile>()
        for (i in (1..8)) {
            val currentTile = board.getTile(tile.row + i, tile.column + i)
            if (currentTile == null || currentTile.hasPiece())
                break
            else if (currentTile.isEmpty())
                list.add(currentTile)
        }
        return list
    }

    private fun downLeftPositionsMoves(tile: Tile, board: Board): List<Tile> {
        val list = mutableListOf<Tile>()
        for (i in (1..8)) {
            val currentTile = board.getTile(tile.row - i, tile.column - i)
            if (currentTile == null || currentTile.hasPiece())
                break
            else if (currentTile.isEmpty())
                list.add(currentTile)
        }
        return list
    }

    private fun downRightPositionsMoves(tile: Tile, board: Board): List<Tile> {
        val list = mutableListOf<Tile>()
        for (i in (1..8)) {
            val currentTile = board.getTile(tile.row - i, tile.column + i)
            if (currentTile == null || currentTile.hasPiece())
                break
            else if (currentTile.isEmpty())
                list.add(currentTile)
        }
        return list

    }

}