package nl.tbvh.lab.puz.binary

class OnlyAlternativeRule(direction: Direction) : RuleListener(direction) {

	override fun apply(move: Move, board: Board): List<Move> {
		var moves: List<Move> = listOf()

		val line = getLine(move, board)

		moves += check(line, '0')
		moves += check(line, '1')

		return moves
	}


	fun check(line: Line, ch: Char): List<Move> {
		val free = line.getFree()
//		if (String(line.cells).startsWith(" 1   011"))
//			println("Found it! " + free.size + "  " + line.available(ch))
		if (free.size - 1 == line.available(ch) && line.available(ch) > 1) {
			// Try all alternatives
//			println(line)
			val filled = free.map { it.copy(value = ch) }.fold(line, { l, f -> l.with(f) })

			var moves: List<Move> = listOf()
			for (c in free) {
				val cell = c.withEmptyValue()
				if (filled.with(cell).invalid()) {
					moves += Move(cell.row, cell.col, ch, "OnlyAlternative-" + direction)
				}
//				println("" + filled.with(cell) + moves)
			}
			return moves
		}
		return emptyList()
	}

}