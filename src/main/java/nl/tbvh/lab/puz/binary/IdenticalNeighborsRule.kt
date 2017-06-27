package nl.tbvh.lab.puz.binary

class IdenticalNeighbors(direction: Direction) : RuleListener(direction) {

	override fun apply(move: Move, board: Board): List<Move> {
		var moves: List<Move> = listOf()

		val line = getLine(move, board)

		moves += check(line, getIndex(move), 1)
		moves += check(line, getIndex(move), -1)

		return moves
	}


	fun check(line: Line, index: Int, offset: Int): List<Move> {
		val self = line.get(index)
		val next = line.get(index + offset)
		if (self == next) {
			return listOf(line.point(index - offset), line.point(index + 2 * offset))
					.filterNotNull()
					.map { p -> Move(p, self.invert(), "IdenticalNeighbors " + direction) }
		}
		return emptyList()
	}

}