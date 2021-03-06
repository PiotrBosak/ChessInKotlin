package chess.board

import Color
import chess.board.exceptions.IllegalTileException
import chess.pieces.Pawn
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class TileTest {

    @Test
    fun whenCreateTile_CurrentPieceAndStartingPieceIsTheSame() {
        val pawn = Pawn(Color.BLACK)
        val tile = Tile(Color.WHITE, 1, 2, pawn)
        assertTrue { tile.currentPiece === tile.startingPiece }
    }

    @Test
    fun whenPassIncorrectCoordinates_ThrowException() {
        val pawn = Pawn(Color.BLACK)

        assertFailsWith<IllegalTileException> { Tile(Color.WHITE, 0, 7, pawn) }
    }

    @Test
    fun whenPassIncorrectColor_ThrowException() {
        val pawn = Pawn(Color.BLACK)
        assertFailsWith<IllegalTileException> { Tile(Color.WHITE, 1, 1,pawn) }
    }
}