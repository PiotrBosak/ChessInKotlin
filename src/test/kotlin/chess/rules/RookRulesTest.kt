package chess.rules

import chess.pieces.Rook
import chess.rules.exceptions.EmptyTileException
import chess.rules.exceptions.WrongRuleException
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class RookRulesTest {
    private val rookRules = RookRules
    @Test
    fun whenGetWrongPiece_ThrowException(){
        assertFailsWith<WrongRuleException> {rookRules.calculatePossibleAttacks(2,2)  }

    }
    @Test
    fun whenGetEmptyTile_ThrowException(){
        assertFailsWith<EmptyTileException> {rookRules.calculatePossibleAttacks(4,5)  }
    }

    @Test
    fun whenTryToMoveBeingBlocked_NumberOfMovesIsZero(){
        val possibleMoves = rookRules.calculatePossibleMoves(1,1)
        assertEquals(0,possibleMoves.size)

    }
}