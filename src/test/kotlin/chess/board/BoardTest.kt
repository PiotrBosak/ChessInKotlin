package chess.board

import Color.*
import chess.pieces.*
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class BoardTest {
    private val board: Board = Board()


    @Test
    fun whenCreateBoard_ItHas64Tiles() {
        assertEquals(board.tiles.size, 64)
    }

    @Test
    fun whenCreateBoard_ItHas32WhiteTiles() {
        assertEquals(board.tiles.filter { it.color == WHITE }.count(), 32)
    }

    @Test
    fun whenCreateBoard_ItHas32BlackTiles() {
        assertEquals(board.tiles.filter { it.color == BLACK }.count(), 32)
    }

    @Test
    fun whenCreateBoard_ItHas16Pawns() {
        assertEquals(16, board.tiles.filter { it.startingPiece is Pawn }.count())
    }

    @Test
    fun whenCreateBoard_ItHas8BlackPawns() {
        assertEquals(8, board.tiles.filter { it.startingPiece is Pawn }.filter { it.color == BLACK }.count())
    }

    @Test
    fun whenCreateBoard_ItHas2Kings() {
        assertEquals(2, board.tiles.filter { it.startingPiece is King }.count())
    }

    @Test
    fun whenCreateBoard_ItHas4Rooks() {
        assertEquals(4, board.tiles.filter { it.startingPiece is Rook }.count())
    }

    @Test
    fun whenCreateBoard_ItHas4Bishops() {
        assertEquals(4, board.tiles.filter { it.startingPiece is Bishop }.count())

    }

    @Test
    fun whenCreateBoard_ItHas2WhiteKnights() {
        assertEquals(2, board.tiles.filter { it.startingPiece is Knight }.filter { it.color == WHITE }.count())
    }

}