package nl.tbvh.lab.puz.binary

class OnlyOptionRule(direction: Direction) : RuleListener(direction) {

	override fun apply(move: Move, board: Board): List<Move> {
		var moves: List<Move> = listOf()

		val line = getLine(move, board)

		moves += check(line, '0', '1')
		moves += check(line, '1', '0')

		return moves
	}


	fun check(line: Line, c: Char, inverse: Char): List<Move> {
		val cells = line.getAll(c)
		if (cells.size == line.cells.size / 2)
			return line.getFree()
					.map { c -> Move(c.row, c.col, inverse, "OnlyOption-" + direction) }
		return emptyList()
	}

}