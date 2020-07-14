package chess.rules

import chess.board.Board
import chess.rules.exceptions.EmptyTileException
import chess.rules.exceptions.WrongRuleException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.lang.RuntimeException
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class PawnRulesTest {
    private var board: Board = Board()

    @BeforeEach
    fun setUp() {
        board = Board()
    }

    @Test
    fun whenMovePawnFirstTime_TwoTileMoveAvailable() {
        val moves = PawnRules.calculatePossibleMoves(2, 1, board)
        assertEquals(2, moves.size)
    }

    @Test
    fun whenPickTileWithNoPiece_ThrowException() {
        assertFailsWith<EmptyTileException> {
            PawnRules.calculatePossibleMoves(3, 5, board)
        }
    }


    @Test
    fun whenPickTileWithNoPawn_ThrowException() {
        assertFailsWith<WrongRuleException> {
            PawnRules.calculatePossibleMoves(1, 5, board)
        }
    }

    @Test
    fun whenMovePawnOnce_NextOneMoveAvailable() {
        val startingTile = board.getTile(2, 1) ?: throw RuntimeException()
        val destinationTile = board.getTile(3, 1) ?: throw RuntimeException()
        board.makeMoveWithoutCheckingMate(startingTile, destinationTile)
        val moves = PawnRules.calculatePossibleMoves(destinationTile.row, destinationTile.column, board)
        assertEquals(1, moves.size)
    }

    @Test
    fun whenMovePawnTwoTiles_NextOneMoveAvailable() {
        val startingTile = board.getTile(2, 1) ?: throw RuntimeException()
        val destinationTile = board.getTile(4, 1) ?: throw RuntimeException()
        board.makeMoveWithoutCheckingMate(startingTile, destinationTile)
        val moves = PawnRules.calculatePossibleMoves(destinationTile.row, destinationTile.column, board)
        assertEquals(1, moves.size)
    }

    @Test
    fun movePawn3Times() {
        val startingTile = board.getTile(2, 1) ?: throw RuntimeException()
        val firstDest = board.getTile(4, 1) ?: throw RuntimeException()
        board.makeMoveWithoutCheckingMate(startingTile, firstDest)
        val secondDest = board.getTile(5, 1) ?: throw RuntimeException()
        board.makeMoveWithoutCheckingMate(firstDest, secondDest)
        val thirdDest = board.getTile(6, 1) ?: throw RuntimeException()
        board.makeMoveWithoutCheckingMate(secondDest, thirdDest)
        val moves = PawnRules.calculatePossibleMoves(6, 1, board)
        assertEquals(0, moves.size)
    }

    @Test
    fun whitePawnCapturesBlackPawn() {
        val startingTile = board.getTile(2, 1) ?: throw RuntimeException()
        val startingPiece = startingTile.currentPiece
        val firstDest = board.getTile(4, 1) ?: throw RuntimeException()
        board.makeMoveWithoutCheckingMate(startingTile, firstDest)
        val secondDest = board.getTile(5, 1) ?: throw RuntimeException()
        board.makeMoveWithoutCheckingMate(firstDest, secondDest)
        val thirdDest = board.getTile(6, 1) ?: throw RuntimeException()
        board.makeMoveWithoutCheckingMate(secondDest, thirdDest)
        val fourthDest = board.getTile(7, 2) ?: throw RuntimeException()
        board.makeAttack(thirdDest, fourthDest)
        assertTrue { fourthDest.currentPiece == startingPiece }
    }

    @Test
    fun blackPawnCapturesWhitePawn() {
        val startingTile = board.getTile(7, 3) ?: throw RuntimeException()
        val startingPiece = startingTile.currentPiece
        val firstDest = board.getTile(5, 3) ?: throw RuntimeException()
        board.makeMoveWithoutCheckingMate(startingTile, firstDest)
        val secondDest = board.getTile(4, 3) ?: throw RuntimeException()
        board.makeMoveWithoutCheckingMate(firstDest, secondDest)
        val thirdDest = board.getTile(3, 3) ?: throw RuntimeException()
        board.makeMoveWithoutCheckingMate(secondDest, thirdDest)
        val fourthDest = board.getTile(2, 4) ?: throw RuntimeException()
        board.makeAttack(thirdDest, fourthDest)
        assertTrue { fourthDest.currentPiece == startingPiece }
    }

    @Test
    fun blackPawnCapturesWhitePawnAndThenCapturesNextPiece() {
        val startingTile = board.getTile(7, 3) ?: throw RuntimeException()
        val startingPiece = startingTile.currentPiece
        val firstDest = board.getTile(5, 3) ?: throw RuntimeException()
        board.makeMoveWithoutCheckingMate(startingTile, firstDest)
        val secondDest = board.getTile(4, 3) ?: throw RuntimeException()
        board.makeMoveWithoutCheckingMate(firstDest, secondDest)
        val thirdDest = board.getTile(3, 3) ?: throw RuntimeException()
        board.makeMoveWithoutCheckingMate(secondDest, thirdDest)
        val fourthDest = board.getTile(2, 4) ?: throw RuntimeException()
        board.makeAttack(thirdDest, fourthDest)
        val fifthDest = board.getTile(1, 5) ?: throw RuntimeException()
        board.makeAttack(fourthDest, fifthDest)
        assertTrue { fifthDest.currentPiece == startingPiece }
    }

    @Test
    fun blackDoesLePassant() {
        val blackStartingTile = board.getTile(7, 3) ?: throw RuntimeException()
        val blackFirstDest = board.getTile(5, 3) ?: throw RuntimeException()
        val blackSecondDest = board.getTile(4, 3) ?: throw RuntimeException()
        val whiteStartingTile = board.getTile(2, 4) ?: throw RuntimeException()
        val whiteDestTile = board.getTile(4, 4) ?: throw RuntimeException()
        val blackThirdDest = board.getTile(3, 4) ?: throw RuntimeException()
        board.makeMoveWithoutCheckingMate(blackStartingTile, blackFirstDest)
        board.makeMoveWithoutCheckingMate(blackFirstDest, blackSecondDest)
        board.makeMoveWithoutCheckingMate(whiteStartingTile, whiteDestTile)
        board.makeAttack(blackSecondDest, blackThirdDest)

    }

    @Test
    fun whenBlackPawnTriesLePassant_ButWhitePawnDidNotMoveByTwoTiles_ThrowException() {
        val blackStartingTile = board.getTile(7, 3) ?: throw RuntimeException()
        val blackFirstDest = board.getTile(5, 3) ?: throw RuntimeException()
        val blackSecondDest = board.getTile(4, 3) ?: throw RuntimeException()
        val whiteStartingTile = board.getTile(2, 4) ?: throw RuntimeException()
        val whiteFirstDest = board.getTile(3, 4) ?: throw RuntimeException()
        val whiteSecondDest = board.getTile(4, 4) ?: throw RuntimeException()
        val blackThirdDest = board.getTile(3, 4) ?: throw RuntimeException()
        board.makeMoveWithoutCheckingMate(blackStartingTile, blackFirstDest)
        board.makeMoveWithoutCheckingMate(blackFirstDest, blackSecondDest)
        board.makeMoveWithoutCheckingMate(whiteStartingTile, whiteFirstDest)
        board.makeMoveWithoutCheckingMate(whiteFirstDest, whiteSecondDest)
        assertFails { board.makeAttack(blackSecondDest, blackThirdDest) }
    }

    @Test
    fun whitePawnDoesLePassant() {
        val whiteStartingTile = board.getTile(2, 3) ?: throw RuntimeException()
        val whiteFirstDestTile = board.getTile(4, 3) ?: throw RuntimeException()
        val whiteSecondDestTile = board.getTile(5, 3) ?: throw RuntimeException()
        val blackStartingTile = board.getTile(7, 4) ?: throw RuntimeException()
        val blackFirstDest = board.getTile(5, 4) ?: throw RuntimeException()
        val whiteThirdDest = board.getTile(6, 4) ?: throw RuntimeException()
        board.makeMoveWithoutCheckingMate(whiteStartingTile, whiteFirstDestTile)
        board.makeMoveWithoutCheckingMate(whiteFirstDestTile, whiteSecondDestTile)
        board.makeMoveWithoutCheckingMate(blackStartingTile, blackFirstDest)
        board.makeAttack(whiteSecondDestTile, whiteThirdDest)
    }


}