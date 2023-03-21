package com.fovsol.tictactoe.tictactoe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.fovsol.tictactoe.R
import com.fovsol.tictactoe.databinding.FragmentGameDrawBinding

class GameDrawFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentGameDrawBinding>(
            inflater,
            R.layout.fragment_game_draw, container, false
        )

        binding.playButton.setOnClickListener {
            it.findNavController().navigate(GameDrawFragmentDirections.actionGameDrawFragmentToGameFragment())
        }

        return binding.root
    }
}