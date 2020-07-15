package chess.rules

import Color
import Color.BLACK
import Color.WHITE
import chess.board.Board
import chess.pieces.King

object CheckRules {
    fun isAllyKingCheckedAfterMove(board: Board, color: Color): Boolean {
        val oppositeColor = if (color == WHITE) BLACK else WHITE
        val tileWithKing = board.tiles
                .filter { it.currentPiece != null }
                .filter { it.currentPiece!! is King }
                .first { it.currentPiece!!.color == color }
        return board.tiles
                .filter { it.currentPiece != null }
                .filter { it.currentPiece!!.color == oppositeColor }
                .flatMap(board::getAppropriateAttacks)
                .any { l -> l === tileWithKing }
    }

    fun isEnemyKingCheckedAfterMove(board: Board, color: Color): Boolean {
        val oppositeColor = if (color == WHITE) BLACK else WHITE
        return isAllyKingCheckedAfterMove(board, oppositeColor)
    }


}