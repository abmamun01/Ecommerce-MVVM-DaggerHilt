package com.mamunsproject.ecommerce_mvvm_dg.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.mamunsproject.ecommerce_mvvm_dg.data.User
import com.mamunsproject.ecommerce_mvvm_dg.utils.*
import com.mamunsproject.ecommerce_mvvm_dg.utils.Constants.USER_COLLECTION
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val db: FirebaseFirestore
) : ViewModel() {

    // For observing fragment we use Mutable
    //receive user as flow
    private val _register = MutableStateFlow<Resources<User>>(Resources.Unspecified())

    // to receive as a flow
    val register: Flow<Resources<User>> = _register


    private val _validation = Channel<RegisterFieldsState>()

    // to receive as a flow
    val validation = _validation.receiveAsFlow()


    fun createAccountWithEmailAndPassword(user: User, password: String) {

        if (checkValidation(user, password)) {

            runBlocking {
                _register.emit(Resources.Loading())
            }

            firebaseAuth.createUserWithEmailAndPassword(user.email, password).addOnSuccessListener {
                // Saving user data when register success
                it.user?.let {
                    saveUserInfo(it.uid, user)
                    //  _register.value = Resources.Success(it)
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

    private fun saveUserInfo(userUid: String, user: User) {

        // creating collection
        db.collection(USER_COLLECTION)
            .document(userUid)
            //saving Document
            .set(user)
            .addOnSuccessListener {
                   _register.value = Resources.Success(user)

            }.addOnFailureListener {
                _register.value = Resources.Error(it.message.toString())

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