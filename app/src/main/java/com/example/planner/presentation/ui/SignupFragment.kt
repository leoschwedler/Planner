package com.example.planner.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.planner.R
import com.example.planner.databinding.FragmentSignupBinding
import com.example.planner.presentation.viewmodel.UserRegistrationViewModel
import com.example.planner.util.imageBitmapToBase64
import com.example.planner.util.imageUriToBitmap
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignupFragment : Fragment() {

    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!
    private val navController by lazy { findNavController() }
    private val viewmodel: UserRegistrationViewModel by viewModels()
    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                binding.imageViewAdd.setImageURI(uri)
                val imageBitMap = requireContext().imageUriToBitmap(uri)
                if (imageBitMap != null){
                    val imageBase64 = imageBitmapToBase64(bitmap = imageBitMap)
                    viewmodel.updateImage(image = imageBase64)
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    "Ops... nenhuma foto selecionada",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
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
            viewmodel.uiState.collect { state ->
                with(binding) {
                    buttonSaveUser.isEnabled = state.isProfileValid
                }
            }
        }
    }

    private fun setupClickListeners() {
        with(binding) {
            buttonSaveUser.setOnClickListener {
                viewmodel.saveProfile()
                navController.navigate(R.id.action_signupFragment_to_homeFragment)
            }
            imageViewAdd.setOnClickListener {
                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
            editTextName.addTextChangedListener {
                viewmodel.updateName(it.toString())
            }
            editTextEmail.addTextChangedListener {
                viewmodel.updateEmail(it.toString())
            }
            editTextTelephone.addTextChangedListener {
                viewmodel.updateTelephone(it.toString())
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}