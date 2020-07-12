package chess.rules

import chess.board.Board
import chess.board.Tile
import chess.pieces.Piece
import chess.pieces.Rook
import chess.rules.exceptions.EmptyTileException
import chess.rules.exceptions.WrongRuleException

object RookRules : Rules {
    override fun calculatePossibleMoves(row: Int, column: Int, board: Board): List<Tile> {
        val currentTile = board.getTile(row, column) ?: return emptyList()
        validatePiece(currentTile.currentPiece)
        return getPossibleMoves(currentTile, board)
    }

    override fun calculatePossibleAttacks(row: Int, column: Int, board: Board): List<Tile> {
        val currentTile = board.getTile(row, column) ?: return emptyList()
        validatePiece(currentTile.currentPiece)
        return getPossibleAttacks(currentTile, board)
    }

    fun getPossibleAttacks(tile: Tile, board: Board): List<Tile> {
        return listOfNotNull(getDownAttack(tile, board), getTopAttack(tile, board),
                getLeftAttack(tile, board), getRightAttack(tile, board))
    }

    private fun getDownAttack(tile: Tile, board: Board): Tile? {
        for (i in (1..8)) {
            val currentTile = board.getTile(tile.row - i, tile.column)
            if (currentTile == null)
                break
            else if (currentTile.hasPiece()) {
                if (currentTile.currentPiece!!.color != tile.currentPiece!!.color)
                    return currentTile

            }
        }
        return null
    }

    private fun getLeftAttack(tile: Tile, board: Board): Tile? {
        for (i in (1..8)) {
            val currentTile = board.getTile(tile.row, tile.column - i)
            if (currentTile == null)
                break
            else if (currentTile.hasPiece()) {
                if (currentTile.currentPiece!!.color != tile.currentPiece!!.color)
                    return currentTile

            }
        }
        return null
    }

    private fun getRightAttack(tile: Tile, board: Board): Tile? {
        for (i in (1..8)) {
            val currentTile = board.getTile(tile.row, tile.column + i)
            if (currentTile == null)
                break
            else if (currentTile.hasPiece()) {
                if (currentTile.currentPiece!!.color != tile.currentPiece!!.color)
                    return currentTile

            }
        }
        return null
    }

    private fun getTopAttack(tile: Tile, board: Board): Tile? {
        for (i in (1..8)) {
            val currentTile = board.getTile(tile.row + i, tile.column)
            if (currentTile == null)
                break
            else if (currentTile.hasPiece()) {
                if (currentTile.currentPiece!!.color != tile.currentPiece!!.color)
                    return currentTile

            }
        }
        return null
    }

    override fun validatePiece(piece: Piece?) {
        if (piece == null)
            throw EmptyTileException()
        else if ((piece is Rook).not())
            throw WrongRuleException()
    }

    fun getPossibleMoves(tile: Tile, board: Board): List<Tile> {
        return getLeftMoves(tile, board) + getRightMoves(tile, board) + getTopMoves(tile, board) + getDownMoves(tile, board)
    }

    private fun getDownMoves(tile: Tile, board: Board): List<Tile> {
        val list = mutableListOf<Tile>()
        for (i in (1..8)) {
            val currentTile = board.getTile(tile.row - i, tile.column)
            if (currentTile == null || currentTile.hasPiece())
                break
            else if (currentTile.isEmpty())
                list.add(currentTile)
        }
        return list
    }

    private fun getLeftMoves(tile: Tile, board: Board): List<Tile> {
        val list = mutableListOf<Tile>()
        for (i in (1..8)) {
            val currentTile = board.getTile(tile.row, tile.column - i)
            if (currentTile == null || currentTile.hasPiece())
                break
            else if (currentTile.isEmpty())
                list.add(currentTile)
        }
        return list
    }

    private fun getRightMoves(tile: Tile, board: Board): List<Tile> {
        val list = mutableListOf<Tile>()
        for (i in (1..8)) {
            val currentTile = board.getTile(tile.row, tile.column + i)
            if (currentTile == null || currentTile.hasPiece())
                break
            else if (currentTile.isEmpty())
                list.add(currentTile)
        }
        return list
    }

    private fun getTopMoves(tile: Tile, board: Board): List<Tile> {
        val list = mutableListOf<Tile>()
        for (i in (1..8)) {
            val currentTile = board.getTile(tile.row + i, tile.column)
            if (currentTile == null || currentTile.hasPiece())
                break
            else if (currentTile.isEmpty())
                list.add(currentTile)
        }
        return list
    }


}