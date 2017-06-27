package nl.tbvh.lab.puz.binary

data class Point(val row: Int, val col: Int) {
	
	fun get(board:Board): Char{
		return board.get(this)!!
	}
}