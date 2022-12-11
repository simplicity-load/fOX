package com.fovsol.tictactoe

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.fovsol.tictactoe.databinding.FragmentGameBinding

class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameBinding
    private lateinit var ticTacToe: TicTacToe

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_game, container, false
        )

        // Initialize the logic and the UI
        initializeGame()

        // Initialize onClickListeners functions
        binding.apply {
            buttonFirst.setOnClickListener { button(it) }
            buttonSecond.setOnClickListener { button(it) }
            buttonThird.setOnClickListener { button(it) }
            buttonFourth.setOnClickListener { button(it) }
            buttonFifth.setOnClickListener { button(it) }
            buttonSixth.setOnClickListener { button(it) }
            buttonSeventh.setOnClickListener { button(it) }
            buttonEighth.setOnClickListener { button(it) }
            buttonNinth.setOnClickListener { button(it) }
            GameOverText.setOnClickListener { restartGame() }
        }
        return binding.root
    }


    // If playerXTurn is true return X, useful when starting
    private fun XorOResource(): Int = if (ticTacToe.playerXTurn) R.drawable.x else R.drawable.o

    // We need to return the opposite since this is going to be called after we make a play
    private fun XorOResourceRev(): Int =
        if (!ticTacToe.playerXTurn) R.drawable.x else R.drawable.o

    @SuppressLint("SetTextI18n")
    fun button(view: View) {
        val nrBut: Pair<Int, Int> = matchButtons(view)
        if (ticTacToe.play(nrBut.first, nrBut.second)) {
            if (!ticTacToe.gameFinished) { // If game hasn't finished change playerImage
                binding.playerImage.setImageResource(XorOResource()) // Get current player's image
            }
            // Put an X/O on the button that was clicked
            (view as ImageView).setImageResource(XorOResourceRev()) // Get last player's image

            // If game has finished take appropriate action,
            // if not check if boardFilled() and if true restartGame()
            if (ticTacToe.gameFinished) {
                binding.GameOverText.text =
                    "Game Over, ${if (!ticTacToe.playerXTurn) "X" else "O"} won!\nPress here to restart game!"
            } else if (ticTacToe.boardFilled)
                restartGame()
        }
    }

    private fun matchButtons(view: View): Pair<Int, Int> = when (view.id) {
        R.id.buttonFirst -> Pair(0, 0)
        R.id.buttonSecond -> Pair(0, 1)
        R.id.buttonThird -> Pair(0, 2)

        R.id.buttonFourth -> Pair(1, 0)
        R.id.buttonFifth -> Pair(1, 1)
        R.id.buttonSixth -> Pair(1, 2)

        R.id.buttonSeventh -> Pair(2, 0)
        R.id.buttonEighth -> Pair(2, 1)
        R.id.buttonNinth -> Pair(2, 2)

        else -> Pair(3, 3)
    }

    private fun initializeGame() {
        this.ticTacToe = TicTacToe()
        binding.apply {
            playerImage.setImageResource(XorOResource())
            buttonFirst.setImageResource(0)
            buttonSecond.setImageResource(0)
            buttonThird.setImageResource(0)
            buttonFourth.setImageResource(0)
            buttonFifth.setImageResource(0)
            buttonSixth.setImageResource(0)
            buttonSeventh.setImageResource(0)
            buttonEighth.setImageResource(0)
            buttonNinth.setImageResource(0)
            GameOverText.text = ""
        }
    }

    private fun restartGame() = initializeGame()
}
