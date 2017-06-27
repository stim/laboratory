package nl.tbvh.lab.puz.binary;

import java.lang.IllegalArgumentException


enum class Direction {
	Horizontal, Vertical
	;

	fun offset(offset: Int, dir: Direction): Int {
		if (this != dir)
			return 0
		return offset
	}

}

public abstract class RuleListener(val direction: Direction) {

	fun getNeighbor(move: Move, offset: Int, board: Board): Char? {
		return getNeighbor(move.row, move.col, offset, board)
	}

	fun getNeighbor(row: Int, col: Int, offset: Int, board: Board): Char? {
		val point = offset(row, col, offset, board);
		return point?.get(board)
	}

	fun offset(move: Move, offset: Int, board: Board): Point? {
		return offset(move.row, move.col, offset, board)
	}

	fun offset(p: Point, offset: Int, board: Board): Point? {
		return offset(p.row, p.col, offset, board)
	}

	fun offset(row: Int, col: Int, offset: Int, board: Board): Point? {
		val r = direction.offset(offset, Direction.Horizontal) + row
		val c = direction.offset(offset, Direction.Vertical) + col
		if (!board.has(r) || !board.has(c))
			return null
		return Point(r, c)
	}

	fun invert(ch: Char): Char {
		when (ch) {
			'0' -> return '1'
			'1' -> return '0'
		}
		throw IllegalArgumentException("Invalid char " + ch)
	}

	fun getLine(move: Move, board: Board): Line {
		when (direction) {
			Direction.Horizontal -> return Line.row(move.row, board)
			Direction.Vertical -> return Line.col(move.col, board)
		}
	}

	fun getIndex(move: Move): Int {
		when (direction) {
			Direction.Horizontal -> return move.col
			Direction.Vertical -> return move.row
		}
	}

	abstract fun apply(move: Move, board: Board): List<Move>
}
