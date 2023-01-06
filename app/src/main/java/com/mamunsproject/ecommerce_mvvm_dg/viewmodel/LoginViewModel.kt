package com.mamunsproject.ecommerce_mvvm_dg.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.mamunsproject.ecommerce_mvvm_dg.utils.Resources
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    // this time we will use shared flow which is one time event
    // whenever login will be success we will navigate to another page
    private val _login = MutableSharedFlow<Resources<FirebaseUser>>()
    val login = _login.asSharedFlow()


    fun login(email: String, password: String) {

        viewModelScope.launch { _login.emit(Resources.Loading()) }
        firebaseAuth.signInWithEmailAndPassword(
            email, password
        ).addOnSuccessListener {
            viewModelScope.launch {
                it.user?.let {

                    _login.emit(Resources.Success(it))
                }
            }
        }.addOnFailureListener {
            viewModelScope.launch {
                _login.emit(Resources.Error(it.message.toString()))
            }
        }
    }
}