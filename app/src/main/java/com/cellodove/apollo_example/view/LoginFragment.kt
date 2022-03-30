package com.cellodove.apollo_example.view

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.cellodove.apollo_example.databinding.LoginFragmentBinding
import com.cellodove.apollo_example.model.User
import com.cellodove.apollo_example.viewmodel.MainViewModel

class LoginFragment : Fragment() {
    private lateinit var binding: LoginFragmentBinding
    private val viewModel : MainViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LoginFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.submitProgressBar.visibility = View.GONE
        binding.submit.setOnClickListener {
            val email = binding.email.text.toString()
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.emailLayout.error = "Invalid email"
                return@setOnClickListener
            }
            binding.submitProgressBar.visibility = View.VISIBLE
            binding.submit.visibility = View.GONE
            viewModel.loginMutation(email)
        }
        viewModelObserver()
    }

    private fun viewModelObserver(){
        viewModel.loginMutationToken.observe(viewLifecycleOwner){ response ->
            val token = response?.data?.login?.token
            if (token == null || response.hasErrors()){
                binding.submitProgressBar.visibility = View.GONE
                binding.submit.visibility = View.VISIBLE
                return@observe
            }
            User.setToken(requireContext(), token)
            findNavController().popBackStack()
        }
    }
}
