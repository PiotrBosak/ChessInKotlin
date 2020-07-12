package chess.rules

import chess.rules.exceptions.EmptyTileException
import chess.rules.exceptions.WrongRuleException
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class PawnRulesTest {
    private val pawnRules = PawnRules

    @Test
    fun whenMovePawnFirstTime_TwoTileMoveAvailable() {
        val moves = pawnRules.calculatePossibleMoves(2, 1)
        assertEquals(2, moves.size)
    }

    @Test
    fun whenPickTileWithNoPiece_ThrowException() {
        assertFailsWith<EmptyTileException> {
            val move = pawnRules.calculatePossibleMoves(3, 5)
        }
    }


    @Test
    fun whenPickTileWithNoPawn_ThrowException() {
        assertFailsWith<WrongRuleException> {
            val move = pawnRules.calculatePossibleMoves(1, 5)
        }
    }
}