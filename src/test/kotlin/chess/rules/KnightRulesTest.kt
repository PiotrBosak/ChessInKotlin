package chess.rules

import chess.board.Board
import chess.rules.exceptions.EmptyTileException
import chess.rules.exceptions.WrongRuleException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.lang.RuntimeException
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class KnightRulesTest {
    private val knightRules = KnightRules
    private var board = Board()

    @BeforeEach
    private fun setUp() {
        board = Board()
    }

    @Test
    fun whenGetWrongPiece_ThrowException() {
        assertFailsWith<WrongRuleException> { knightRules.calculatePossibleAttacks(2, 2, board) }

    }

    @Test
    fun whenGetEmptyTile_ThrowException() {
        assertFailsWith<EmptyTileException> { knightRules.calculatePossibleAttacks(4, 5, board) }
    }

    @Test
    fun whenTryToMoveAtTheBeginning_NumberOfMovesIsTwo() {
        val possibleMoves = knightRules.calculatePossibleMoves(1, 2, board)
        assertEquals(2, possibleMoves.size)
    }

    @Test
    fun moveWhiteKnightCoupleOfTime() {
        val startingTile = board.getTile(1, 7) ?: throw RuntimeException()
        val firstDest = board.getTile(3, 6) ?: throw RuntimeException()
        val secondDest = board.getTile(4, 4) ?: throw RuntimeException()
        val thirdDest = board.getTile(5, 2) ?: throw RuntimeException()
        board.makeMoveWithoutCheckingMate(startingTile, firstDest)
        board.makeMoveWithoutCheckingMate(firstDest, secondDest)
        board.makeMoveWithoutCheckingMate(secondDest, thirdDest)
        val moves = knightRules.calculatePossibleMoves(thirdDest.row, thirdDest.column, board)
        val attacks = knightRules.calculatePossibleAttacks(thirdDest.row, thirdDest.column, board)
        assertEquals(4, moves.size)
        assertEquals(2, attacks.size)

    }

    @Test
    fun blackKnightCaptures() {
        val startingTile = board.getTile(8, 2) ?: throw RuntimeException()
        val firstDest = board.getTile(6, 3) ?: throw RuntimeException()
        val secondDest = board.getTile(4, 4) ?: throw RuntimeException()
        val thirdDest = board.getTile(2, 5) ?: throw RuntimeException()
        val fourthDest = board.getTile(1, 3) ?: throw RuntimeException()
        board.makeMoveWithoutCheckingMate(startingTile, firstDest)
        board.makeMoveWithoutCheckingMate(firstDest, secondDest)
        board.makeAttackWithoutCheckingMate(secondDest, thirdDest)
        board.makeAttackWithoutCheckingMate(thirdDest, fourthDest)
        val moves = knightRules.calculatePossibleMoves(fourthDest.row, fourthDest.column, board)
        val attacks = knightRules.calculatePossibleAttacks(fourthDest.row, fourthDest.column, board)
        assertEquals(1, attacks.size)
        assertEquals(3, moves.size)
    }


}