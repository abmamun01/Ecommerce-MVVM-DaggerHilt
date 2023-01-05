package com.mamunsproject.ecommerce_mvvm_dg.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.mamunsproject.ecommerce_mvvm_dg.data.User
import com.mamunsproject.ecommerce_mvvm_dg.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    // For observing fragment we use Mutable
    private val _register = MutableStateFlow<Resources<FirebaseUser>>(Resources.Unspecified())

    // to receive as a flow
    val register: Flow<Resources<FirebaseUser>> = _register


    private val _validation = Channel<RegisterFieldsState>()
    // to receive as a flow
    val validation = _validation.receiveAsFlow()




    fun createAccountWithEmailAndPassword(user: User, password: String) {

        if (checkValidation(user, password)) {

            runBlocking {
                _register.emit(Resources.Loading())
            }

            firebaseAuth.createUserWithEmailAndPassword(user.email, password).addOnSuccessListener {

                it.user?.let {
                    _register.value = Resources.Success(it)
                }
            }.addOnFailureListener {
                _register.value = Resources.Error(it.message.toString())
            }

        } else {
            val registerFieldState = RegisterFieldsState(
                validateEmail(user.email), validatePassword(password)
            )
            runBlocking {
                _validation.send(registerFieldState)
            }
        }
    }

    private fun checkValidation(user: User, password: String): Boolean {
        val emailValidation = validateEmail(user.email)
        val passwordValidation = validatePassword(password)
        val shouldRegister = emailValidation is RegisterValidation.Success &&
                passwordValidation is RegisterValidation.Success

        return shouldRegister
    }
}