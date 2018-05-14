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
            MutableList(9, { MutableList(9, { 0 }) })

    constructor() {
        generatePuzzle()
    }

    private fun generatePuzzle() {
        val valueRestrictions: MutableList<MutableList<MutableSet<Int>>> =
                MutableList(9, { MutableList(9, { mutableSetOf<Int>() })})

        var fieldIndex = 0;
        while (fieldIndex < 9 * 9) {

            val topIndex: Int = fieldIndex / 9;
            val leftIndex: Int = fieldIndex % 9;
            puzzleRows[topIndex][leftIndex] = 0;
            val choice = generateValueAt(topIndex, leftIndex, valueRestrictions[topIndex][leftIndex]);
            if (choice > 0) {
                puzzleRows[topIndex][leftIndex] = choice
                valueRestrictions[topIndex][leftIndex].add(choice)
                fieldIndex++;
            } else {
                if (fieldIndex == 0)
                    throw Error("Failing on first field: ${valueRestrictions[topIndex][leftIndex].sorted()}")
                valueRestrictions[topIndex][leftIndex].clear();
                fieldIndex--;
            }
        }
    }

    fun generateValueAt(topIndex: Int, leftIndex: Int, restrictions:Set<Int> = setOf()):Int {
        val possibilities = possibleValuesAt(topIndex, leftIndex) - restrictions
        if (possibilities.isEmpty()) return 0
        else return possibilities.takeOneRandomly()

        123456789
        4561237
    }

    fun possibleValuesAt(topIndex: Int, leftIndex: Int):Set<Int> =
            (setOf(1,2,3,4,5,6,7,8,9) - getUsedValuesAt(topIndex, leftIndex))

    fun getUsedValuesAt(topIndex: Int, leftIndex: Int):Set<Int> {
        val rowValues = puzzleRows[topIndex].toSet()
        val colValues = puzzleRows.map({ it[leftIndex] }).toSet()
        val squareValues = getRectangleValuesAt(topIndex, leftIndex)
        return (rowValues + colValues + squareValues) - setOf(0, puzzleRows[topIndex][leftIndex])
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