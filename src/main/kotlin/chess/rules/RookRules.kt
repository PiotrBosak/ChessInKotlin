package chess.rules

import chess.board.Tile
import chess.pieces.Piece
import chess.pieces.Rook
import chess.rules.exceptions.EmptyTileException
import chess.rules.exceptions.WrongRuleException

object RookRules : Rules {
    override fun calculatePossibleMoves(row: Int, column: Int): List<Tile> {
        val currentTile = RookRules.board.getTile(row, column) ?: return emptyList()
        validatePiece(currentTile.currentPiece)
        return getPossibleMoves(currentTile)
    }

    override fun calculatePossibleAttacks(row: Int, column: Int): List<Tile> {
        val currentTile = RookRules.board.getTile(row, column) ?: return emptyList()
        validatePiece(currentTile.currentPiece)
        return getPossibleAttacks(currentTile)
    }

     fun getPossibleAttacks(tile: Tile): List<Tile> {
        return listOfNotNull(getDownAttack(tile), getTopAttack(tile),
                getLeftAttack(tile), getRightAttack(tile))
    }

    private fun getDownAttack(tile: Tile): Tile? {
        for (i in (1..8)) {
            val currentTile = RookRules.board.getTile(tile.row - i, tile.column)
            if (currentTile == null)
                break
            else if (currentTile.hasPiece()) {
                if (currentTile.currentPiece!!.color != tile.currentPiece!!.color)
                    return currentTile

            }
        }
        return null
    }

    private fun getLeftAttack(tile: Tile): Tile? {
        for (i in (1..8)) {
            val currentTile = RookRules.board.getTile(tile.row, tile.column - i)
            if (currentTile == null)
                break
            else if (currentTile.hasPiece()) {
                if (currentTile.currentPiece!!.color != tile.currentPiece!!.color)
                    return currentTile

            }
        }
        return null
    }

    private fun getRightAttack(tile: Tile): Tile? {
        for (i in (1..8)) {
            val currentTile = RookRules.board.getTile(tile.row, tile.column + i)
            if (currentTile == null)
                break
            else if (currentTile.hasPiece()) {
                if (currentTile.currentPiece!!.color != tile.currentPiece!!.color)
                    return currentTile

            }
        }
        return null
    }

    private fun getTopAttack(tile: Tile): Tile? {
        for (i in (1..8)) {
            val currentTile = RookRules.board.getTile(tile.row + i, tile.column)
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

     fun getPossibleMoves(tile: Tile): List<Tile> {
        return getLeftMoves(tile) + getRightMoves(tile) + getTopMoves(tile) + getDownMoves(tile)
    }

    private fun getDownMoves(tile: Tile): List<Tile> {
        val list = mutableListOf<Tile>()
        for (i in (1..8)) {
            val currentTile = RookRules.board.getTile(tile.row - i, tile.column)
            if (currentTile == null || currentTile.hasPiece())
                break
            else if (currentTile.isEmpty())
                list.add(currentTile)
        }
        return list
    }

    private fun getLeftMoves(tile: Tile): List<Tile> {
        val list = mutableListOf<Tile>()
        for (i in (1..8)) {
            val currentTile = RookRules.board.getTile(tile.row, tile.column - i)
            if (currentTile == null || currentTile.hasPiece())
                break
            else if (currentTile.isEmpty())
                list.add(currentTile)
        }
        return list
    }

    private fun getRightMoves(tile: Tile): List<Tile> {
        val list = mutableListOf<Tile>()
        for (i in (1..8)) {
            val currentTile = RookRules.board.getTile(tile.row, tile.column + i)
            if (currentTile == null || currentTile.hasPiece())
                break
            else if (currentTile.isEmpty())
                list.add(currentTile)
        }
        return list
    }

    private fun getTopMoves(tile: Tile): List<Tile> {
        val list = mutableListOf<Tile>()
        for (i in (1..8)) {
            val currentTile = RookRules.board.getTile(tile.row + i, tile.column)
            if (currentTile == null || currentTile.hasPiece())
                break
            else if (currentTile.isEmpty())
                list.add(currentTile)
        }
        return list
    }


}