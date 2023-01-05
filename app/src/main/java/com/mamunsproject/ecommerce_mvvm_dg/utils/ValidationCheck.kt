package com.mamunsproject.ecommerce_mvvm_dg.utils

import android.util.Patterns

fun validateEmail(email: String): RegisterValidation {

    if (email.isEmpty()) {
        return RegisterValidation.Failed("Email cannot be Empty")
    }
    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        return RegisterValidation.Failed("Wrong Email Format")


    return RegisterValidation.Success
}


fun validatePassword(password: String): RegisterValidation {

    if (password.isEmpty())
        return RegisterValidation.Failed("Password cannot be Empty")

    if (password.length < 8) {
        return RegisterValidation.Failed("Password cannot be less then 8")

    }

    return RegisterValidation.Success
}