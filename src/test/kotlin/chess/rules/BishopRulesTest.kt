package chess.rules

import chess.board.Board
import chess.rules.exceptions.EmptyTileException
import chess.rules.exceptions.WrongRuleException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.lang.RuntimeException
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class BishopRulesTest {
    private val bishopRules: BishopRules = BishopRules
    private var board: Board = Board()

    @BeforeEach
    fun setUp() {
        board = Board()
    }

    @Test
    fun whenGetWrongPiece_ThrowException() {
        assertFailsWith<WrongRuleException> { bishopRules.calculatePossibleMoves(2, 7, board) }
    }

    @Test
    fun whenGetEmptyTile_ThrowException() {
        assertFailsWith<EmptyTileException> { bishopRules.calculatePossibleMoves(3, 7, board) }
    }

    @Test
    fun moveWhiteBishop() {
        val bishopStartingTile = board.getTile(1, 3) ?: throw RuntimeException()
        val bishopDestTile = board.getTile(6, 8) ?: throw RuntimeException()
        val pawnStartingTile = board.getTile(2, 4) ?: throw RuntimeException()
        val pawnDestMove = board.getTile(3, 4) ?: throw RuntimeException()
        board.makeMove(pawnStartingTile, pawnDestMove)
        board.makeMove(bishopStartingTile, bishopDestTile)
        val moves = bishopRules.calculatePossibleMoves(bishopDestTile.row, bishopDestTile.column, board)
        assertEquals(5, moves.size)
    }

    @Test
    fun moveBlackBishop() {
        val bishopStartingTile = board.getTile(8, 6) ?: throw RuntimeException()
        val bishopFirstDest = board.getTile(5, 3) ?: throw RuntimeException()
        val bishopSecondDest = board.getTile(4, 4) ?: throw RuntimeException()
        val pawnStartingTile = board.getTile(7, 5) ?: throw RuntimeException()
        val pawnDestMove = board.getTile(5, 5) ?: throw RuntimeException()
        board.makeMove(pawnStartingTile, pawnDestMove)
        board.makeMove(bishopStartingTile, bishopFirstDest)
        board.makeMove(bishopFirstDest, bishopSecondDest)
        val moves = bishopRules.calculatePossibleMoves(bishopSecondDest.row, bishopSecondDest.column, board)
        assertEquals(4, moves.size)
    }

    @Test
    fun whiteBishopCaptures() {
        val bishopStartingTile = board.getTile(1, 3) ?: throw RuntimeException()
        val startingBishop = bishopStartingTile.currentPiece
        val bishopFirstDest = board.getTile(6, 8) ?: throw RuntimeException()
        val bishopSecondDest = board.getTile(7, 7) ?: throw RuntimeException()
        val pawnStartingTile = board.getTile(2, 4) ?: throw RuntimeException()
        val pawnDestMove = board.getTile(3, 4) ?: throw RuntimeException()
        board.makeMove(pawnStartingTile, pawnDestMove)
        board.makeMove(bishopStartingTile, bishopFirstDest)
        board.makeAttack(bishopFirstDest, bishopSecondDest)
        val moves = bishopRules.calculatePossibleMoves(bishopSecondDest.row, bishopSecondDest.column, board)
        val attacks = bishopRules.calculatePossibleAttacks(bishopSecondDest.row, bishopSecondDest.column, board)
        assertEquals(5, moves.size)
        assertEquals(2, attacks.size)
        assertTrue { startingBishop == bishopSecondDest.currentPiece }
    }

    @Test
    fun blackBishopCaptures() {
        val bishopStartingTile = board.getTile(8, 3) ?: throw RuntimeException()
        val startingBishop = bishopStartingTile.currentPiece
        val bishopFirstDest = board.getTile(5, 6) ?: throw RuntimeException()
        val bishopSecondDest = board.getTile(2, 3) ?: throw RuntimeException()
        val pawnStartingTile = board.getTile(7, 4) ?: throw RuntimeException()
        val pawnDestMove = board.getTile(5, 4) ?: throw RuntimeException()
        board.makeMove(pawnStartingTile, pawnDestMove)
        board.makeMove(bishopStartingTile, bishopFirstDest)
        board.makeAttack(bishopFirstDest, bishopSecondDest)
        val moves = bishopRules.calculatePossibleMoves(bishopSecondDest.row, bishopSecondDest.column, board)
        val attacks = bishopRules.calculatePossibleAttacks(bishopSecondDest.row, bishopSecondDest.column, board)
        assertEquals(6, moves.size)
        assertEquals(2, attacks.size)
        assertTrue { startingBishop === bishopSecondDest.currentPiece }
    }
}