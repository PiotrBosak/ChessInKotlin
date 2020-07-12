package chess.rules

import chess.rules.exceptions.EmptyTileException
import chess.rules.exceptions.WrongRuleException
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

class BishopRulesTest {
    private val bishopRules: BishopRules = BishopRules

    @Test
    fun whenGetWrongPiece_ThrowException() {
        assertFailsWith<WrongRuleException> { bishopRules.calculatePossibleMoves(2, 7) }
    }

    @Test
    fun whenGetEmptyTile_ThrowException() {
        assertFailsWith<EmptyTileException> { bishopRules.calculatePossibleMoves(3, 7) }
    }
}