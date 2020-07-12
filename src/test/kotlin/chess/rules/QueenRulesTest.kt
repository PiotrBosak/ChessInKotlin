package chess.rules

import chess.board.Board
import chess.rules.exceptions.EmptyTileException
import chess.rules.exceptions.WrongRuleException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.lang.RuntimeException
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class QueenRulesTest {
    private val queenRulesTest = QueenRules
    private var board = Board()

    @BeforeEach
    private fun setUp() {
        board = Board()
    }

    @Test
    fun whenGetWrongPiece_ThrowException() {
        assertFailsWith<WrongRuleException> { queenRulesTest.calculatePossibleMoves(1, 1, board) }

    }

    @Test
    fun whenGetEmptyFile_ThrowException() {
        assertFailsWith<EmptyTileException> { queenRulesTest.calculatePossibleMoves(3, 4, board) }

    }

    @Test
    fun moveWhiteQueen() {
        val pawnStartingTile = board.getTile(2, 5) ?: throw RuntimeException()
        val pawnDestMove = board.getTile(4, 5) ?: throw RuntimeException()
        val queenStartTile = board.getTile(1, 4) ?: throw RuntimeException()
        val queenFirstDest = board.getTile(3, 6) ?: throw RuntimeException()
        val queenSecondDest = board.getTile(3, 3) ?: throw RuntimeException()
        board.makeMove(pawnStartingTile, pawnDestMove)
        board.makeMove(queenStartTile, queenFirstDest)
        board.makeMove(queenFirstDest, queenSecondDest)
        val moves = queenRulesTest.calculatePossibleMoves(queenSecondDest.row, queenSecondDest.column, board)
        val attacks = queenRulesTest.calculatePossibleAttacks(queenSecondDest.row, queenSecondDest.column, board)
        assertEquals(15, moves.size)
        assertEquals(2, attacks.size)
    }

    @Test
    fun blackQueenCaptures() {
        val pawnStartingTile = board.getTile(7, 5) ?: throw RuntimeException()
        val pawnDestMove = board.getTile(5, 5) ?: throw RuntimeException()
        val queenStartTile = board.getTile(8, 4) ?: throw RuntimeException()
        val queenFirstDest = board.getTile(5, 7) ?: throw RuntimeException()
        val queenSecondDest = board.getTile(2, 7) ?: throw RuntimeException()
        board.makeMove(pawnStartingTile, pawnDestMove)
        board.makeMove(queenStartTile, queenFirstDest)
        board.makeAttack(queenFirstDest, queenSecondDest)
        val moves = queenRulesTest.calculatePossibleMoves(queenSecondDest.row, queenSecondDest.column, board)
        val attacks = queenRulesTest.calculatePossibleAttacks(queenSecondDest.row, queenSecondDest.column, board)
        assertEquals(9, moves.size)
        assertEquals(5, attacks.size)

    }


}