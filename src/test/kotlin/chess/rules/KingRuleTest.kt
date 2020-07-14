package chess.rules

import chess.board.Board
import chess.board.exceptions.IllegalMoveException
import chess.pieces.King
import chess.pieces.Rook
import chess.rules.exceptions.EmptyTileException
import chess.rules.exceptions.WrongRuleException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.lang.RuntimeException
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

object KingRuleTest {
    private val kingRules = KingRules
    private var board = Board()

    @BeforeEach
    private fun setUp() {
        board = Board()
    }

    @Test
    fun whenGetWrongPiece_ThrowException() {
        assertFailsWith<WrongRuleException> { kingRules.calculatePossibleAttacks(2, 2, board) }

    }

    @Test
    fun whenGetEmptyTile_ThrowException() {
        assertFailsWith<EmptyTileException> { kingRules.calculatePossibleAttacks(4, 5, board) }
    }

    @Test
    fun whenTryToMoveAtTheBeginning_NumberOfMovesIsZero() {
        val possibleMoves = kingRules.calculatePossibleMoves(1, 5, board)
        assertEquals(0, possibleMoves.size)
    }

    @Test
    fun whiteKingMoves() {
        val pawnStartingTile = board.getTile(2, 5) ?: throw RuntimeException()
        val pawnDestTile = board.getTile(4, 5) ?: throw RuntimeException()
        val kingStartingTile = board.getTile(1, 5) ?: throw RuntimeException()
        val tile2 = board.getTile(2, 5) ?: throw RuntimeException()
        val tile3 = board.getTile(3, 5) ?: throw RuntimeException()
        val tile4 = board.getTile(4, 4) ?: throw RuntimeException()
        val tile5 = board.getTile(5, 4) ?: throw RuntimeException()
        val tile6 = board.getTile(6, 4) ?: throw RuntimeException()
        board.makeMoveWithoutCheckingMate(pawnStartingTile, pawnDestTile)
        board.makeMoveWithoutCheckingMate(kingStartingTile, tile2)
        board.makeMoveWithoutCheckingMate(tile2, tile3)
        board.makeMoveWithoutCheckingMate(tile3, tile4)
        board.makeMoveWithoutCheckingMate(tile4, tile5)
        board.makeMoveWithoutCheckingMate(tile5, tile6)
        val moves = kingRules.calculatePossibleMoves(tile6.row, tile6.column, board)
        val attacks = kingRules.calculatePossibleAttacks(tile6.row, tile6.column, board)
        assertEquals(5, moves.size)
        assertEquals(3, attacks.size)
    }

    @Test
    fun blackKingCaptures() {
        val pawnStartingTile = board.getTile(7, 5) ?: throw RuntimeException()
        val pawnDestTile = board.getTile(5, 5) ?: throw RuntimeException()
        val kingStartingTile = board.getTile(8, 5) ?: throw RuntimeException()
        val tile2 = board.getTile(7, 5) ?: throw RuntimeException()
        val tile3 = board.getTile(6, 5) ?: throw RuntimeException()
        val tile4 = board.getTile(5, 4) ?: throw RuntimeException()
        val tile5 = board.getTile(4, 4) ?: throw RuntimeException()
        val tile6 = board.getTile(3, 4) ?: throw RuntimeException()
        val tile7 = board.getTile(2, 5) ?: throw RuntimeException()
        val tile8 = board.getTile(1, 6) ?: throw RuntimeException()
        board.makeMoveWithoutCheckingMate(pawnStartingTile, pawnDestTile)
        board.makeMoveWithoutCheckingMate(kingStartingTile, tile2)
        board.makeMoveWithoutCheckingMate(tile2, tile3)
        board.makeMoveWithoutCheckingMate(tile3, tile4)
        board.makeMoveWithoutCheckingMate(tile4, tile5)
        board.makeMoveWithoutCheckingMate(tile5, tile6)
        board.makeAttack(tile6, tile7)
        board.makeAttack(tile7, tile8)
        val moves = kingRules.calculatePossibleMoves(tile8.row, tile8.column, board)
        val attacks = kingRules.calculatePossibleAttacks(tile8.row, tile8.column, board)
        assertEquals(1, moves.size)
        assertEquals(4, attacks.size)
    }

    @Test
    fun whiteKingCastlesLeft() {
        val pawnStartingTile = board.getTile(2, 4) ?: throw RuntimeException()
        val pawnDestTile = board.getTile(4, 4) ?: throw RuntimeException()
        val queenStartingTile = board.getTile(1, 4) ?: throw RuntimeException()
        val queenDestTile = board.getTile(3, 4) ?: throw RuntimeException()
        val bishopStartingTile = board.getTile(1, 3) ?: throw RuntimeException()
        val bishopDestTile = board.getTile(4, 6) ?: throw RuntimeException()
        val knightStartTile = board.getTile(1, 2) ?: throw RuntimeException()
        val knightDestTile = board.getTile(3, 1) ?: throw RuntimeException()
        val kingStartingTile = board.getTile(1, 5) ?: throw RuntimeException()
        val destTile = board.getTile(1, 3) ?: throw RuntimeException()
        board.makeMoveWithoutCheckingMate(pawnStartingTile, pawnDestTile)
        board.makeMoveWithoutCheckingMate(queenStartingTile, queenDestTile)
        board.makeMoveWithoutCheckingMate(bishopStartingTile, bishopDestTile)
        board.makeMoveWithoutCheckingMate(knightStartTile, knightDestTile)
        board.makeMoveWithoutCheckingMate(kingStartingTile, destTile)
        assertTrue { destTile.currentPiece is King }
        assertTrue { queenStartingTile.currentPiece is Rook }

    }

    @Test
    fun whiteKingCastlesRight() {
        val pawnStartingTile = board.getTile(2, 5) ?: throw RuntimeException()
        val pawnDestTile = board.getTile(4, 5) ?: throw RuntimeException()
        val bishopStartingTile = board.getTile(1, 6) ?: throw RuntimeException()
        val bishopDestTile = board.getTile(5, 2) ?: throw RuntimeException()
        val knightStartTile = board.getTile(1, 7) ?: throw RuntimeException()
        val knightDestTile = board.getTile(3, 8) ?: throw RuntimeException()
        val kingStartingTile = board.getTile(1, 5) ?: throw RuntimeException()
        val destTile = board.getTile(1, 7) ?: throw RuntimeException()
        board.makeMoveWithoutCheckingMate(pawnStartingTile, pawnDestTile)
        board.makeMoveWithoutCheckingMate(bishopStartingTile, bishopDestTile)
        board.makeMoveWithoutCheckingMate(knightStartTile, knightDestTile)
        board.makeMoveWithoutCheckingMate(kingStartingTile, destTile)
        assertTrue { destTile.currentPiece is King }
        assertTrue { bishopStartingTile.currentPiece is Rook }
    }

    @Test
    fun blackKingCastlesLeft() {
        val pawnStartingTile = board.getTile(7, 4) ?: throw RuntimeException()
        val pawnDestTile = board.getTile(5, 4) ?: throw RuntimeException()
        val queenStartingTile = board.getTile(8, 4) ?: throw RuntimeException()
        val queenDestTile = board.getTile(6, 4) ?: throw RuntimeException()
        val bishopStartingTile = board.getTile(8, 3) ?: throw RuntimeException()
        val bishopDestTile = board.getTile(5, 6) ?: throw RuntimeException()
        val knightStartTile = board.getTile(8, 2) ?: throw RuntimeException()
        val knightDestTile = board.getTile(6, 1) ?: throw RuntimeException()
        val kingStartingTile = board.getTile(8, 5) ?: throw RuntimeException()
        val destTile = board.getTile(8, 3) ?: throw RuntimeException()
        board.makeMoveWithoutCheckingMate(pawnStartingTile, pawnDestTile)
        board.makeMoveWithoutCheckingMate(queenStartingTile, queenDestTile)
        board.makeMoveWithoutCheckingMate(bishopStartingTile, bishopDestTile)
        board.makeMoveWithoutCheckingMate(knightStartTile, knightDestTile)
        board.makeMoveWithoutCheckingMate(kingStartingTile, destTile)
        assertTrue { destTile.currentPiece is King }
        assertTrue { queenStartingTile.currentPiece is Rook }
    }

    @Test
    fun blackKingCastlesRight() {
        val pawnStartingTile = board.getTile(7, 5) ?: throw RuntimeException()
        val pawnDestTile = board.getTile(5, 5) ?: throw RuntimeException()
        val bishopStartingTile = board.getTile(8, 6) ?: throw RuntimeException()
        val bishopDestTile = board.getTile(4, 2) ?: throw RuntimeException()
        val knightStartTile = board.getTile(8, 7) ?: throw RuntimeException()
        val knightDestTile = board.getTile(6, 8) ?: throw RuntimeException()
        val kingStartingTile = board.getTile(8, 5) ?: throw RuntimeException()
        val destTile = board.getTile(8, 7) ?: throw RuntimeException()
        board.makeMoveWithoutCheckingMate(pawnStartingTile, pawnDestTile)
        board.makeMoveWithoutCheckingMate(bishopStartingTile, bishopDestTile)
        board.makeMoveWithoutCheckingMate(knightStartTile, knightDestTile)
        board.makeMoveWithoutCheckingMate(kingStartingTile, destTile)
        assertTrue { destTile.currentPiece is King }
        assertTrue { bishopStartingTile.currentPiece is Rook }
    }

    @Test
    fun whenWhiteKingHasMoved_HeCannotCastle() {
        val pawnStartingTile = board.getTile(2, 5) ?: throw RuntimeException()
        val pawnDestTile = board.getTile(4, 5) ?: throw RuntimeException()
        val bishopStartingTile = board.getTile(1, 6) ?: throw RuntimeException()
        val bishopDestTile = board.getTile(5, 2) ?: throw RuntimeException()
        val knightStartTile = board.getTile(1, 7) ?: throw RuntimeException()
        val knightDestTile = board.getTile(3, 8) ?: throw RuntimeException()
        val kingStartingTile = board.getTile(1, 5) ?: throw RuntimeException()
        val destTile = board.getTile(1, 7) ?: throw RuntimeException()
        board.makeMoveWithoutCheckingMate(pawnStartingTile, pawnDestTile)
        board.makeMoveWithoutCheckingMate(bishopStartingTile, bishopDestTile)
        board.makeMoveWithoutCheckingMate(knightStartTile, knightDestTile)
        board.makeMoveWithoutCheckingMate(kingStartingTile, bishopStartingTile)
        board.makeMoveWithoutCheckingMate(bishopStartingTile, kingStartingTile)
        assertFailsWith<IllegalMoveException> { board.makeMoveWithoutCheckingMate(kingStartingTile, destTile) }
    }

}