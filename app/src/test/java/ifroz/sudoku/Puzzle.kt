package ifroz.sudoku

import ifroz.sudoku.app.Puzzle;
import org.junit.Test
//import org.junit.Assert.*

class PuzzleGenerator {
    @Test
    fun puzzle_instantiates() {
        Puzzle()
    }

    @Test
    fun puzzle_hasNoZeros() {
        val puzzle = Puzzle()
        try {
            for (row in puzzle.toMutableList()) {
                for (item in row) {
                    assert(item in 1..9)
                }
            }
        } catch (err: Error) {
            throw Error("Sudoku containing zeros: \n$puzzle")
        }
    }
}