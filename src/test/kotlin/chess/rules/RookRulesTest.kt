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

class RookRulesTest {
    private val rookRules = RookRules
    private var board: Board = Board()

    @BeforeEach
    fun setUp() {
        board = Board()
    }

    @Test
    fun whenGetWrongPiece_ThrowException() {
        assertFailsWith<WrongRuleException> { rookRules.calculatePossibleAttacks(2, 2, board) }

    }

    @Test
    fun whenGetEmptyTile_ThrowException() {
        assertFailsWith<EmptyTileException> { rookRules.calculatePossibleAttacks(4, 5, board) }
    }

    @Test
    fun whenTryToMoveBeingBlocked_NumberOfMovesIsZero() {
        val possibleMoves = rookRules.calculatePossibleMoves(1, 1, board)
        assertEquals(0, possibleMoves.size)

    }

    @Test
    fun moveWhiteRook() {
        val rookStartingTile = board.getTile(1, 1) ?: throw RuntimeException()
        val rookFirstDest = board.getTile(3, 1) ?: throw RuntimeException()
        val rookSecondDest = board.getTile(3, 8) ?: throw RuntimeException()
        val pawnStartingTile = board.getTile(2, 1) ?: throw RuntimeException()
        val pawnDestTile = board.getTile(4, 1) ?: throw RuntimeException()
        board.makeMoveWithoutCheckingMate(pawnStartingTile, pawnDestTile)
        board.makeMoveWithoutCheckingMate(rookStartingTile, rookFirstDest)
        board.makeMoveWithoutCheckingMate(rookFirstDest, rookSecondDest)
        val moves = rookRules.calculatePossibleMoves(rookSecondDest.row, rookSecondDest.column, board)
        assertEquals(10, moves.size)
    }

    @Test
    fun moveBlackRook() {
        val rookStartingTile = board.getTile(8, 8) ?: throw RuntimeException()
        val rookFirstDest = board.getTile(6, 8) ?: throw RuntimeException()
        val rookSecondDest = board.getTile(6, 4) ?: throw RuntimeException()
        val pawnStartingTile = board.getTile(7, 8) ?: throw RuntimeException()
        val pawnDestTile = board.getTile(5, 8) ?: throw RuntimeException()
        board.makeMoveWithoutCheckingMate(pawnStartingTile, pawnDestTile)
        board.makeMoveWithoutCheckingMate(rookStartingTile, rookFirstDest)
        board.makeMoveWithoutCheckingMate(rookFirstDest, rookSecondDest)
        val moves = rookRules.calculatePossibleMoves(rookSecondDest.row, rookSecondDest.column, board)
        assertEquals(10, moves.size)
    }

    @Test
    fun whiteRookCaptures() {
        val rookStartingTile = board.getTile(1, 1) ?: throw RuntimeException()
        val startingPiece = rookStartingTile.currentPiece
        val rookFirstDest = board.getTile(3, 1) ?: throw RuntimeException()
        val rookSecondDest = board.getTile(3, 8) ?: throw RuntimeException()
        val rookThirdDest = board.getTile(7, 8) ?: throw RuntimeException()
        val rookFourthDest = board.getTile(8, 8) ?: throw RuntimeException()
        val rookFifthDest = board.getTile(8, 7) ?: throw RuntimeException()
        val pawnStartingTile = board.getTile(2, 1) ?: throw RuntimeException()
        val pawnDestTile = board.getTile(4, 1) ?: throw RuntimeException()

        board.makeMoveWithoutCheckingMate(pawnStartingTile, pawnDestTile)
        board.makeMoveWithoutCheckingMate(rookStartingTile, rookFirstDest)
        board.makeMoveWithoutCheckingMate(rookFirstDest, rookSecondDest)
        board.makeAttack(rookSecondDest, rookThirdDest)
        board.makeAttack(rookThirdDest, rookFourthDest)
        board.makeAttack(rookFourthDest, rookFifthDest)
        val moves = rookRules.calculatePossibleMoves(rookFifthDest.row, rookFifthDest.column, board)
        val attacks = rookRules.calculatePossibleAttacks(rookFifthDest.row, rookFifthDest.column, board)
        assertEquals(1, moves.size)
        assertEquals(2, attacks.size)
        assertTrue { rookFifthDest.currentPiece === startingPiece }
    }


}