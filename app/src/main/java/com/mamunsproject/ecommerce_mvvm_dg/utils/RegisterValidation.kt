package com.mamunsproject.ecommerce_mvvm_dg.utils

// we have two possible option
//1. all input are correct 2.incorrect
sealed class RegisterValidation() {

    object Success : RegisterValidation()
    data class Failed(val message: String) : RegisterValidation()

}

data class RegisterFieldsState(
    val email: RegisterValidation,
    val password: RegisterValidation
)