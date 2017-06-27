package nl.tbvh.lab.puz.binary


class Line(val row: Int?, val col: Int?, vararg val cells: Char) {
	companion object {
		fun row(row: Int, board: Board): Line {
			var a: CharArray = charArrayOf()
			for (col in 0 until board.side) {
				a += board.getSafely(row, col)
			}
			return Line(row, null, *a)
		}

		fun col(col: Int, board: Board): Line {
			var a: CharArray = charArrayOf()
			for (row in 0 until board.side) {
				a += board.getSafely(row, col)
			}
			return Line(null, col, *a)
		}

	}

	fun with(cell: Cell): Line {
		val cs = cells.copyOf()
		cs.set(indexOf(cell), cell.value!!)
		return Line(row, col, *cs)
	}

	fun indexOf(cell: Cell): Int {
		if (row == null) {
			return cell.row
		}
		return cell.col
	}

	fun get(index: Int): Cell {
		if (index < 0 || index >= cells.size)
			return Cell(row ?: index, col ?: index, null)
		return Cell(row ?: index, col ?: index, cells.get(index))
	}

	fun available(ch: Char): Int = cells.size / 2 - getAll(ch).size

	fun getAll(): List<Cell> {
		return (0..cells.size).map { i -> get(i) }
	}

	fun getFree(): List<Cell> {
		return getAll()
				.filter { it.isEmpty() }
	}

	fun getAll(c: Char): List<Cell> {
		return getAll()
				.filter { it.value == c }
	}

	fun invalid(): Boolean {
		return !valid()
	}

	fun valid(): Boolean {
		return cells.fold(Triple(0, Board.EMPTY, true), { st, ch ->
			//			println(" " + st + " " + ch)
			if (ch == Board.EMPTY) {
//				println("empty")
				st.copy(second = ch)
			} else if (st.second == ch) {
//			    println("match")
				st.copy(first = st.first + 1, third = st.third && st.first < 2)
			} else {
//			    println("first")
				st.copy(first = 1, second = ch)
			}
		}).third
	}

	fun point(index: Int): Point? {
		if (row == null)
			return point(index, col!!)
		return point(row, index)
	}

	private fun point(row: Int, col: Int): Point? {
		if (row < 0 || row >= cells.size || col < 0 || col >= cells.size)
			return null
		return Point(row, col)
	}

	override fun toString(): String {
		var str = ""
		if (row == null)
			str += "col(" + col + "):"
		else
			str += "row(" + row + "):"
		str += cells.contentToString()
		return str
	}
}


data class Cell(val row: Int, val col: Int, val value: Char?) {

	fun isEmpty(): Boolean {
		return value != null && value == Board.EMPTY
	}

	fun withEmptyValue(): Cell {
		return copy(value = Board.EMPTY)
	}

	fun invert(): Char {
		when (value) {
			'0' -> return '1'
			'1' -> return '0'
		}
		throw IllegalArgumentException("Invalid char " + value)
	}

	override fun equals(other: Any?): Boolean {
		if (other !is Cell)
			return false
		else
			return other.value == value && value != null && value != Board.EMPTY
	}
}