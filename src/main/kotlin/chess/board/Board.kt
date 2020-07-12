package chess.board


class Board {
    val tiles: List<Tile> = BoardFactory.createTiles()
    fun getTile(row: Int, column: Int): Tile? {
        return try {
            tiles.first { it.row == row && it.column == column }
        } catch (e: NoSuchElementException) {
            null
        }

    }

    fun makeMove(startingTile: Tile, destinationTile: Tile) {
    }



}
