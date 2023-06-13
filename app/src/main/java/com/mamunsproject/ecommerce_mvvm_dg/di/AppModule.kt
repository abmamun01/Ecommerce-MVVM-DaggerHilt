package com.mamunsproject.ecommerce_mvvm_dg.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import com.bumptech.glide.ListPreloader.PreloadModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mamunsproject.ecommerce_mvvm_dg.utils.Constants.INTRODUCTION_SP
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()


    @Provides
    @Singleton
    fun provideFirebaseFireFirestoreDatabase() = Firebase.firestore


    @Provides
    fun provideIntroductionSP(
        application: Application
    ) = application.getSharedPreferences(INTRODUCTION_SP , MODE_PRIVATE)
}