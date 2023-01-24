package com.mamunsproject.ecommerce_mvvm_dg.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mamunsproject.ecommerce_mvvm_dg.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LogInRegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_register)
    }
}