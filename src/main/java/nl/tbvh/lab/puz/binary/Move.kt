package nl.tbvh.lab.puz.binary

class Move(val row: Int, val col: Int, val value: Char, val reason: String = "") {

	constructor(point: Point, value: Char, reason: String = "") : this(point.row, point.col, value, reason)

	override fun toString(): String {
		return reason + " " + row + ", " + col
	}
}