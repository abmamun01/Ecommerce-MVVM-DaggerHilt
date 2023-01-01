package com.mamunsproject.ecommerce_mvvm_dg.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.mamunsproject.ecommerce_mvvm_dg.data.User
import com.mamunsproject.ecommerce_mvvm_dg.utils.Resources
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    // For observing fragment we use Mutable
    private val _register = MutableStateFlow<Resources<FirebaseUser>>(Resources.Loading())
    val register: Flow<Resources<FirebaseUser>> = _register


    fun createAccountWithEmailAndPassword(user: User, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(user.email, password)
            .addOnSuccessListener {

                it.user?.let {
                    _register.value = Resources.Success(it)
                }
            }.addOnFailureListener {

                _register.value = Resources.Error(it.message.toString())
            }
    }
}