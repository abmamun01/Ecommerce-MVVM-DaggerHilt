package com.mamunsproject.ecommerce_mvvm_dg.fragments.loginRegister

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mamunsproject.ecommerce_mvvm_dg.R
import com.mamunsproject.ecommerce_mvvm_dg.databinding.FragmentAcountOptionBinding

import com.mamunsproject.ecommerce_mvvm_dg.databinding.FragmentIntroductionBinding

class AccountOptionFragment : Fragment() {
    private lateinit var binding: FragmentAcountOptionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAcountOptionBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonLoginAccountOption.setOnClickListener {
            findNavController().navigate(R.id.action_accountOptionFragment_to_loginFragment)
        }


        binding.buttonRegisterAccountOption.setOnClickListener {
            findNavController().navigate(R.id.action_accountOptionFragment_to_registerFragment)
        }
    }
}