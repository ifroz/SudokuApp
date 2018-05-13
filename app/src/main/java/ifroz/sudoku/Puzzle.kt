package ifroz.sudoku.app

import java.util.*

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

fun <E> Set<E>.takeOneRandomly(): E {
    if (size == 0)
        throw Error("Cannot take random element of an empty set.")
    return toMutableList().shuffle()[0];
}

class Puzzle {
    var puzzleRows:MutableList<MutableList<Int>> =
            MutableList(9, { _ -> MutableList(9, { 0 }) })

    constructor() {
        generatePuzzle()
    }

    private fun generatePuzzle() {
        for (topIndex in 0..4) {
            for (leftIndex in 0..8) {
                generateValueAt(topIndex, leftIndex)
            }
        }
    }

    fun generateValueAt(topIndex: Int, leftIndex: Int) {
        val usedValues = getUsedValuesAt(topIndex, leftIndex)
        val possibleValues = setOf(1,2,3,4,5,6,7,8,9) - usedValues
        puzzleRows[topIndex][leftIndex] = possibleValues.takeOneRandomly()
    }

    fun getUsedValuesAt(topIndex: Int, leftIndex: Int):Set<Int> {
        val rowValues = puzzleRows[topIndex].toSet()
        val colValues = puzzleRows.map({ it[leftIndex] }).toSet()
        val squareValues = getRectangleValuesAt(topIndex, leftIndex)
        return (rowValues + colValues + squareValues) - setOf(0)
    }

    fun getRectangleValuesAt(topIndex: Int, leftIndex: Int):Set<Int> {
        val topRange = (topIndex / 3 * 3)..(topIndex / 3 * 3 + 2)
        val leftRange = (leftIndex / 3 * 3)..(leftIndex / 3 * 3 + 2)
        val valuesInRectangle = puzzleRows.slice(topRange).flatMap { it.slice(leftRange) }.toSet()
        return valuesInRectangle;
    }

    fun toMutableList():MutableList<MutableList<Int>> {
        return puzzleRows;
    }

    override fun toString():String {
        return puzzleRows.fold("", { acc, row -> "$acc${row.joinToString("")}\n" })
    }
}