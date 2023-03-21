package com.fovsol.tictactoe.tictactoe

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.fovsol.tictactoe.R
import com.fovsol.tictactoe.TicTacToe
import com.fovsol.tictactoe.databinding.FragmentGameBinding

class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameBinding
    private var ticTacToe: TicTacToe = TicTacToe()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_game, container, false
        )

        // Assign this fragment to the data binding variable `gameFragment`
        binding.gameFragment = this

        // Set the playerImage to the correct Resource
        binding.playerImage.setImageResource(XorOResource())
        return binding.root
    }


    // If playerXTurn is true return X, useful when starting
    private fun XorOResource(): Int = if (ticTacToe.playerXTurn) R.drawable.x else R.drawable.o

    // We need to return the opposite since this is going to be called after we make a play
    private fun XorOResourceRev(): Int =
        if (!ticTacToe.playerXTurn) R.drawable.x else R.drawable.o

    @SuppressLint("SetTextI18n")
    fun buttonClick(view: View) {
        val nrBut: Pair<Int, Int> = matchButtons(view)
        if (ticTacToe.play(nrBut.first, nrBut.second)) {
            if (!(ticTacToe.gameFinished || ticTacToe.boardFilled)) { // If game hasn't finished or ended in a draw change playerImage
                binding.playerImage.setImageResource(XorOResource()) // Get current player's image
            }
            // Put an X/O on the button that was clicked
            (view as ImageView).setImageResource(XorOResourceRev()) // Get last player's image

            // If game has finished take appropriate action,
            // if not check if boardFilled() and if true restartGame()
            if (ticTacToe.gameFinished) {
                findNavController().navigate(
                    GameFragmentDirections.actionGameFragmentToGameWonFragment(
                        !ticTacToe.playerXTurn
                    )
                )
            } else if (ticTacToe.boardFilled)
                findNavController().navigate(GameFragmentDirections.actionGameFragmentToGameDrawFragment())
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
}
