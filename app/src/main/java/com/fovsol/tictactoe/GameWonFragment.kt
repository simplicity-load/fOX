package com.fovsol.tictactoe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.fovsol.tictactoe.databinding.FragmentGameWonBinding

class GameWonFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var binding = DataBindingUtil.inflate<FragmentGameWonBinding>(
            inflater,
            R.layout.fragment_game_won, container, false
        )

        val args = GameWonFragmentArgs.fromBundle(requireArguments())

        binding.apply {
            playerImage.setImageResource(XorOResource(args.winningPlayer))
            playButton.setOnClickListener {
                it.findNavController()
                    .navigate(GameWonFragmentDirections.actionGameWonFragmentToGameFragment())
            }
        }
        return binding.root
    }

    private fun XorOResource(x: Boolean): Int = if (x) R.drawable.x else R.drawable.o
}