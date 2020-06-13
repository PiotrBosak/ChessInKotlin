package chess.board

import chess.pieces.Pawn
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class BoardTest {
    private var board:Board = Board()
    @BeforeEach
    fun setUp(){
        board = Board()
    }
    @Test
    fun whenCreateBoard_ItHas64Tiles(){
        assertEquals(board.tiles.size,64)
    }
    @Test
    fun whenCreateBoard_ItHas32WhiteTiles(){
        assertEquals(board.tiles.filter{it.color == Color.WHITE}.count(),32)
    }
    @Test
    fun whenCreateBoard_ItHas32BlackTiles(){
        assertEquals(board.tiles.filter{it.color == Color.BLACK}.count(),32)
    }
    @Test
    fun whenCreateBoard_ItHas16Pawns(){
        assertEquals(board.tiles.filter{it.startingPiece is Pawn}.count(),16)
    }

}