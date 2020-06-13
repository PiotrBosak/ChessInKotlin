package chess.pieces

import org.junit.jupiter.api.Test

class PiecesTest {
    @Test
    fun createAllPieces() {
        val color = Color.BLACK
        val pawn = Pawn(color)
        val rook = Rook(color)
        val knight = Knight(color)
        val bishop = Bishop(color)
        val queen = Queen(color)
        val king = King(color)
        
    }
}