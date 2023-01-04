package com.mamunsproject.ecommerce_mvvm_dg.fragments.loginRegister

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.mamunsproject.ecommerce_mvvm_dg.R
import com.mamunsproject.ecommerce_mvvm_dg.data.User
import com.mamunsproject.ecommerce_mvvm_dg.databinding.FragmentRegisterBinding
import com.mamunsproject.ecommerce_mvvm_dg.utils.Resources
import com.mamunsproject.ecommerce_mvvm_dg.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    lateinit var binding: FragmentRegisterBinding
    private val viewModel by viewModels<RegisterViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.apply {
            buttonRegister.setOnClickListener {
                val user = User(
                    edFirstNameRegister.text.toString().trim(),
                    edLastNameRegister.text.toString().trim(),
                    edEmailRegister.text.toString().trim()
                )
                val password = edPassWordRegister.text.toString()
                viewModel.createAccountWithEmailAndPassword(user, password)
            }
        }


        lifecycleScope.launchWhenStarted {
            viewModel.register.collect {
                when (it) {
                    is Resources.Loading -> {
                        binding.buttonRegister.startAnimation()
                    }
                    is Resources.Success -> {
                        Log.d("test", it.data.toString())
                        binding.buttonRegister.revertAnimation()
                    }

                    is Resources.Error -> {
                        Log.d("test", it.message.toString())
                        binding.buttonRegister.revertAnimation()
                    }

                    else -> Unit
                }
            }
        }
    }


}