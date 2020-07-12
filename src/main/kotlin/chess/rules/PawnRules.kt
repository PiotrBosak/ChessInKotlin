package chess.rules

import Color.*
import chess.board.Board
import chess.board.Tile
import chess.pieces.Pawn
import chess.pieces.Piece
import chess.rules.exceptions.EmptyTileException
import chess.rules.exceptions.WrongRuleException

object PawnRules : Rules {
    override fun calculatePossibleMoves(row: Int, column: Int): List<Tile> {
        return listOfNotNull(twoTileMove(row, column), regularMove(row, column))
    }

    override fun calculatePossibleAttacks(row: Int, column: Int): List<Tile> {
        return regularAttacks(row, column).filterNotNull()
        //stil haven't added lePassant(maybe history of the game is needed)
    }

    private fun twoTileMove(row: Int, column: Int): Tile? {
        val currentTile = board.getTile(row, column) ?: return null
        validatePiece(currentTile.currentPiece)
        val secondNextRow = if (currentTile.currentPiece!!.color == WHITE) 2 else -2
        return if (currentTile.hasStartingPiece() && nextTwoTilesEmpty(currentTile))
            board.getTile(row + secondNextRow, column)
        else null

    }

    private fun lePassant(row: Int, column: Int): Tile? {
        return null
    }

    private fun regularMove(row: Int, column: Int): Tile? {
        val currentTile = board.getTile(row, column) ?: return null
        validatePiece(currentTile.currentPiece)
        val nextRow = if (currentTile.currentPiece!!.color == WHITE) 1 else -1
        return if (nextTileEmpty(currentTile))
            board.getTile(row + nextRow, column)
        else null
    }

    private fun regularAttacks(row: Int, column: Int): List<Tile?> {
        val mutableList = mutableListOf<Tile?>()
        val currentTile = board.getTile(row, column) ?: return emptyList()
        validatePiece(currentTile.currentPiece)
        val nextRow = if (currentTile.currentPiece!!.color == WHITE) 1 else -1
        val firstColumn = -1
        val secondColumn = 1
        if (canAttack(currentTile, nextRow, firstColumn))
            mutableList.add(board.getTile(currentTile.row + nextRow, currentTile.column + firstColumn))
        if (canAttack(currentTile, nextRow, secondColumn))
            mutableList.add(board.getTile(currentTile.row + nextRow, currentTile.column + secondColumn))
        return mutableList
    }

    override fun validatePiece(piece: Piece?) {
        if (piece == null)
            throw EmptyTileException()
        else if ((piece is Pawn).not())
            throw WrongRuleException()
    }

    private fun nextTwoTilesEmpty(tile: Tile): Boolean {
        val secondNextRow = if (tile.currentPiece!!.color == WHITE) 2 else -2
        val secondNextTile = board.tiles.first { it.row == tile.row + secondNextRow && it.column == tile.column }
        return nextTileEmpty(tile) && secondNextTile.currentPiece == null

    }

    private fun nextTileEmpty(tile: Tile): Boolean {
        val firstNextRow = if (tile.currentPiece!!.color == WHITE) 1 else -1
        val firstNextTile = board.tiles.first { it.row == tile.row + firstNextRow && it.column == tile.column }
        return firstNextTile.currentPiece == null
    }

    private fun canAttack(tile: Tile, nextRow: Int, nextColumn: Int): Boolean {
        val attackedTile = board.getTile(tile.row + nextRow, tile.column + nextColumn) ?: return false
        return attackedTile.currentPiece != null &&
                attackedTile.currentPiece!!.color != tile.currentPiece!!.color
    }
}