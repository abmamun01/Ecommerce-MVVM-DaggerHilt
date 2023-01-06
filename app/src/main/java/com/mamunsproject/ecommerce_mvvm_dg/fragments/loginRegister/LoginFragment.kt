package com.mamunsproject.ecommerce_mvvm_dg.fragments.loginRegister

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.mamunsproject.ecommerce_mvvm_dg.R
import com.mamunsproject.ecommerce_mvvm_dg.activities.ShoppingActivity
import com.mamunsproject.ecommerce_mvvm_dg.databinding.FragmentLoginBinding
import com.mamunsproject.ecommerce_mvvm_dg.utils.Resources
import com.mamunsproject.ecommerce_mvvm_dg.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel by viewModels<LoginViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.tvDontHaveAccount.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.apply {

            binding.buttonLogin.setOnClickListener {

                val email = edEmailLogin.text.toString().trim()
                val password = edPassWordLogin.text.toString()

                viewModel.login(email, password)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.login.collect {

                when (it) {
                    is Resources.Loading -> {
                        binding.buttonLogin.startAnimation()
                    }
                    is Resources.Success -> {
                        binding.buttonLogin.revertAnimation()
                        Intent(requireActivity(), ShoppingActivity::class.java).also { intent ->
                            // this will ensure that we won't go to this login page again i.e it will remove forever
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)

                            startActivity(intent)
                        }

                    }
                    is Resources.Error -> {
                        binding.buttonLogin.revertAnimation()
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

                    }

                    else -> Unit
                }
            }
        }
    }

}