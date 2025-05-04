package com.example.planner.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.planner.R
import com.example.planner.presentation.components.BottomSheetFragment
import com.example.planner.databinding.FragmentHomeBinding
import com.example.planner.presentation.viewmodel.UserRegistrationViewModel
import com.example.planner.util.imageBase64ToBitmap
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewmodel: UserRegistrationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupClickListeners()
        setupObservers()


    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewmodel.uiState.collect{ profile ->
                with(binding){
                    textViewName.text = getString(R.string.ola_usuario, profile.name)
                    imageBase64ToBitmap(base64String = profile.image)?.let { imageBitmap ->
                        imageViewProfile.setImageBitmap(imageBitmap)
                    }
                }
            }
        }
    }

    private fun setupClickListeners() {
        with(binding){
            buttonSavePlanner.setOnClickListener {
                BottomSheetFragment().show(childFragmentManager, "bottomSheet")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}