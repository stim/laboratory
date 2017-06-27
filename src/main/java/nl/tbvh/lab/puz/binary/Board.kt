package nl.tbvh.lab.puz.binary

//sealed
abstract class Board(val side: Int) {
	companion object {
		val EMPTY = ' ';
	}

	fun apply(move: Move): Board? {
		var cell = get(move.row, move.col);
		if (cell != EMPTY)
			return null

		return NonEmptyBoard(this, move)
	}

	fun get(point: Point): Char? {
		return get(point.row, point.col)
	}

	fun get(row: Int, col: Int): Char? {
		if (has(row) && has(col))
			return getSafely(row, col)
		return null
	}

	fun has(index: Int): Boolean {
		return index >= 0 && index < side
	}

	abstract fun getSafely(row: Int, col: Int): Char

	override fun toString(): String {
		var str = ""
		for (row in 0 until side) {
			for (col in 0 until side) {
				str += getSafely(row, col)
			}
			str += "\n"
		}
		return str
	}
}

class EmptyBoard(side: Int) : Board(side) {

	override fun getSafely(row: Int, col: Int): Char {
		return EMPTY
	}
}

class NonEmptyBoard(val board: Board, val move: Move) : Board(board.side) {

	override fun getSafely(row: Int, col: Int): Char {
		if (row == move.row && col == move.col) {
			return move.value;
		}
		return board.getSafely(row, col)
	}


	override fun toString(): String {
		var str = ""
		if (move.reason != "Puzzle Setup")
			str += board
		str += move
		str += "\n"
		str += super.toString()
		return str
	}
}

