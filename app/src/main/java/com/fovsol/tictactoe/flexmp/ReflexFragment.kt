package com.fovsol.tictactoe.flexmp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.fovsol.tictactoe.R
import com.fovsol.tictactoe.databinding.FragmentReflexBinding

class ReflexFragment : Fragment() {

    private lateinit var binding: FragmentReflexBinding
    private lateinit var viewModel: ReflexViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_reflex, container, false)
        viewModel = ViewModelProvider(this).get(ReflexViewModel::class.java)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        viewModel.topPlayerTurn.observe(viewLifecycleOwner) {
            binding.apply {
                viewModel?.let { vm ->
                    if (!vm.bottomPlayerPenalty.value!! && !vm.topPlayerPenalty.value!!) {
                        if (it and vm.gameStarted) {
                            topField.setImageResource(R.drawable.field_green)
                            bottomField.setImageResource(R.drawable.field_gray)
                        } else if (vm.gameStarted) {
                            topField.setImageResource(R.drawable.field_gray)
                            bottomField.setImageResource(R.drawable.field_green)
                        } else {
                            topField.setImageResource(R.drawable.field_gray)
                            bottomField.setImageResource(R.drawable.field_gray)
                        }
                    }
                }
            }
        }

        viewModel.topPlayerPenalty.observe(viewLifecycleOwner) {
            binding.apply {
                viewModel?.let { vm ->
                    if (vm.gameStarted)
                        if (it) {
                            topField.setImageResource(R.drawable.field_red)
                            bottomField.setImageResource(R.drawable.field_gray)
                        } else {
                            topField.setImageResource(R.drawable.field_green)
                            bottomField.setImageResource(R.drawable.field_gray)
                        }
                }
            }
        }

        viewModel.bottomPlayerPenalty.observe(viewLifecycleOwner) {
            binding.apply {
                viewModel?.let { vm ->
                    if (vm.gameStarted)
                        if (it) {
                            topField.setImageResource(R.drawable.field_gray)
                            bottomField.setImageResource(R.drawable.field_red)
                        } else {
                            topField.setImageResource(R.drawable.field_gray)
                            bottomField.setImageResource(R.drawable.field_green)
                        }
                }
            }
        }

        return binding.root
    }

}