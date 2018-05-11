package ifroz.sudoku.app

import java.util.*
import kotlin.collections.LinkedHashSet

fun <T> MutableList<T>.shuffle(): MutableList<T> {
    val rng = Random()

    for (index in 0..this.size - 1) {
        val randomIndex = rng.nextInt(this.size)

        // Swap with the random position
        val temp = this[index]
        this[index] = this[randomIndex]
        this[randomIndex] = temp
    }

    return this
}

class Puzzle {
    var puzzle:MutableList<MutableList<Int>>

    constructor() {
        puzzle = MutableList(9, { _ -> MutableList(9, { 0 }) });
        generatePuzzle();
    }

    fun generatePuzzle(currentIndex:Int = 0) {
        // first row
        val row = mutableListOf(1,2,3,4,5,6,7,8,9).shuffle()
        setRowAt(0, row);

        // first column
        var idx = 0;
        val usedValues: Set<Int> = row.slice(0..currentIndex).fold(LinkedHashSet(), { acc, value ->
            acc.add(value)
            acc
        });
        val availableValues:Set<Int> = setOf(1,2,3,4,5,6,7,8,9) - usedValues;
        val column = availableValues.toMutableList().shuffle();
        setColumnAt(0, column.toList(), 1);

        // first square
        val usedValuesInSquare = puzzle.slice(0..3).flatMap { it.slice(0..3) }

    }

    fun setRowAt(rowIndex: Int, row: MutableList<Int>) {
        puzzle[rowIndex] = row;
    }

    fun setColumnAt(colIndex: Int, column: List<Int>, startAtRowIndex:Int = 0) {
        var rowIndex = 0;
        while (rowIndex <= column.lastIndex) {
            puzzle[rowIndex + startAtRowIndex][colIndex] = column[rowIndex];
            rowIndex++;
        }
    }

    fun getColumnAt(colIndex: Int):List<Int> {
        return List(9, { index: Int -> puzzle[index][colIndex] })
    }

    fun getSolution():MutableList<MutableList<Int>> {
        return puzzle;
    }
}