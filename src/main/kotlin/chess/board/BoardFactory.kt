package chess.board

import chess.pieces.*

object BoardFactory {
    fun createTiles(): List<Tile> {
        return createTilesWithWhitePawns() +
                createTilesWithBlackPawns() +
                createTilesWithWhiteRooks() +
                createTilesWithBlackRooks() +
                createTilesWithWhiteKnights() +
                createTilesWithBlackKnights() +
                createTilesWithWhiteBishops() +
                createTilesWithBlackBishops() +
                createTileWithWhiteQueen() +
                createTileWithBlackQueen() +
                createTileWithWhiteKing() +
                createTileWithBlackKing() +
                createTilesWithNoPieces()
    }

    private fun createTilesWithBlackBishops(): List<Tile> {
        return listOf(
            Tile(Color.WHITE, 3, 8, Bishop(Color.BLACK)),
            Tile(Color.BLACK, 6, 8, Bishop(Color.BLACK))
        )
    }

    private fun createTilesWithBlackKnights(): List<Tile> {
        return listOf(
            Tile(Color.BLACK, 2, 8, Knight(Color.BLACK)),
            Tile(Color.WHITE, 7, 8, Knight(Color.BLACK))
        )
    }

    private fun createTilesWithBlackRooks(): List<Tile> {
        return listOf(
            Tile(Color.WHITE, 1, 8, Rook(Color.BLACK)),
            Tile(Color.BLACK, 8, 8, Rook(Color.BLACK))
        )
    }

    private fun createTilesWithWhiteBishops(): List<Tile> {
        return listOf(
            Tile(Color.BLACK, 3, 1, Bishop(Color.WHITE)),
            Tile(Color.WHITE, 6, 1, Bishop(Color.WHITE))
        )
    }

    private fun createTilesWithWhiteKnights(): List<Tile> {
        return listOf(
            Tile(Color.WHITE, 2, 1, Knight(Color.WHITE)),
            Tile(Color.BLACK, 7, 1, Knight(Color.WHITE))
        )
    }

    private fun createTilesWithWhiteRooks(): List<Tile> {
        return listOf(
            Tile(Color.BLACK, 1, 1, Rook(Color.WHITE)),
            Tile(Color.WHITE, 8, 1, Rook(Color.WHITE))
        )
    }

    private fun createTileWithBlackKing(): Tile {
        return Tile(Color.WHITE, 5, 8, King(Color.BLACK))
    }

    private fun createTileWithBlackQueen(): Tile {
        return Tile(Color.BLACK, 4, 8, Queen(Color.BLACK))
    }

    private fun createTileWithWhiteKing(): Tile {
        return Tile(Color.BLACK, 5, 1, King(Color.WHITE))
    }

    private fun createTileWithWhiteQueen(): Tile {
        return Tile(Color.WHITE, 4, 1, Queen(Color.WHITE))
    }


    private fun createTilesWithNoPieces(): List<Tile> {
        val mutableList = mutableListOf<Tile>()
        for (row in 3..6)
            for (column in 1..8) {
                if ((row + column) % 2 == 0)
                    mutableList.add(Tile(Color.BLACK, column, row, null))
                else
                    mutableList.add(Tile(Color.WHITE, column, row, null))
            }
        return mutableList
    }

    private fun createTilesWithBlackPawns(): List<Tile> {
        val mutableList = mutableListOf<Tile>()
        val row = 7
        for (column in 1..8)
            if ((row + column) % 2 == 0)
                mutableList.add(Tile(Color.BLACK, column, row, Pawn(Color.BLACK)))
            else
                mutableList.add(Tile(Color.WHITE, column, row, Pawn(Color.BLACK)))

        return mutableList
    }

    private fun createTilesWithWhitePawns(): List<Tile> {
        val mutableList = mutableListOf<Tile>()
        val row = 2
        for (column in 1..8)
            if ((row + column) % 2 == 0)
                mutableList.add(Tile(Color.BLACK, column, row, Pawn(Color.WHITE)))
            else
                mutableList.add(Tile(Color.WHITE, column, row, Pawn(Color.WHITE)))

        return mutableList
    }
    

}