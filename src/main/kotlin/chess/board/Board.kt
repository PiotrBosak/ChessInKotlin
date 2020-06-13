package chess.board

import chess.pieces.Pawn

class Board {
    val tiles: List<Tile> = createTiles()

    private fun createTiles(): List<Tile> {
        return createTilesWithWhitePawns() +
                createTilesWithBlackPawns() +
                createTilesWithWhiteRooks() +
                createTilesWithBlackRooks() +
                createTilesWithWhiteKnights() +
                createTilesWithBlackKnigts() +
                createTilesWithWhiteBishops() +
                createTilesWithBlackBishops() +
                createTileWithWhiteQueen() +
                createTileWithBlackQueen() +
                createTileWithWhiteKing() +
                createTileWithBlackKing() +
                createTilesWithNoPieces()
    }

    private fun createTilesWithBlackBishops(): List<Tile> {
        TODO()
    }

    private fun createTilesWithBlackKnigts(): List<Tile> {
        TODO()
    }

    private fun createTilesWithBlackRooks(): List<Tile> {
        TODO()
    }

    private fun createTilesWithWhiteBishops(): List<Tile> {
        TODO()
    }

    private fun createTilesWithWhiteKnights(): List<Tile> {
        TODO()
    }

    private fun createTilesWithWhiteRooks(): List<Tile> {
        TODO()
    }

    private fun createTileWithBlackKing(): Tile {
        TODO()
    }

    private fun createTileWithBlackQueen(): Tile {
        TODO()
    }

    private fun createTileWithWhiteKing(): Tile {
        TODO()
    }

    private fun createTileWithWhiteQueen(): Tile {
        TODO()
    }


    private fun createTilesWithNoPieces(): List<Tile> {
        val row_column = 1
        return List(24) { i -> Tile(Color.WHITE, row_column, row_column, null) } +
                List(24) { i -> Tile(Color.BLACK, row_column, row_column, null) }
    }

    private fun createTilesWithBlackPawns(): List<Tile> {
        val row_column = 1
        return List(8) { i -> Tile(Color.WHITE, row_column, row_column, Pawn(Color.BLACK)) }
    }

    private fun createTilesWithWhitePawns(): List<Tile> {
        val row_column = 1
        return List(8) { i -> Tile(Color.BLACK, row_column, row_column, Pawn(Color.WHITE)) }
    }

}
