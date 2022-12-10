package com.fovsol.tictactoe

class TicTacToe {
    // Game starts as a blank slate of a matrix filled with 0
    private var board: Array<IntArray> = Array(3) { IntArray(3) { 0 } }

    // Player X starts the game
    var playerXTurn: Boolean = true
        private set

    var gameFinished: Boolean = false
        private set

    var boardFilled: Boolean = false
        private set

    private fun gameFinished(): Boolean {
        // Check for rows that have all 1s or -1s
        board.map { row ->
            (row[0] + row[1] + row[2]).also {
                if (it == 3 || it == -3) return true
            }
        }
        // Check for columns that have all 1s or -1s
        for (i in board.indices)
            (board[0][i] + board[1][i] + board[2][i]).also {
                if (it == 3 || it == -3) return true
            }
        // Check for diagonals
        (board[0][0] + board[1][1] + board[2][2]).also { if (it == 3 || it == -3) return true }
        (board[0][2] + board[1][1] + board[2][0]).also { if (it == 3 || it == -3) return true }
        // Otherwise game has not ended
        return false
    }

    // Check if board is filled
    private fun boardFilled(): Boolean {
        var sum = 0
        board.map { row ->
            sum += row.count { it == 0 }
        }
        return (sum == 0)
    }

    // Put an appropriate X/O on the board
    fun play(i: Int, y: Int): Boolean {
        if (i in 0..2 && y in 0..2 && board[i][y] == 0 && !gameFinished) {
            board[i][y] = if (playerXTurn) 1 else -1
            playerXTurn = !playerXTurn
            gameFinished = gameFinished()
            boardFilled = boardFilled()
            return true
        }
        return false
    }
}