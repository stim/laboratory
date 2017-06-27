package nl.tbvh.lab.puz.binary

class AvoidTripletsRule(direction: Direction) : RuleListener(direction) {

	override fun apply(move: Move, board: Board): List<Move> {
		var moves: List<Move> = listOf()

		val line = getLine(move, board)

		moves += check(line, getIndex(move) - 1)
		moves += check(line, getIndex(move) + 1)

		return moves
	}

	fun check(line: Line, index: Int): List<Move> {
		if (line.get(index).isEmpty())
			return checkNeighbors(line, index)
		return emptyList()
	}


	fun checkNeighbors(line: Line, index: Int): List<Move> {
		val before = line.get(index - 1)
		val after = line.get(index + 1)
		if (before == after) {
			return listOf(Move(line.point(index)!!, after.invert(), "AvoidTripletsRule-" + direction))
		}
		return emptyList()
	}

}