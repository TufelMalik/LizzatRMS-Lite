package com.tufelmalik.lizzatresturentlite.classes

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import dagger.Provides
import javax.inject.Singleton

object FirebaseModule {


    @Provides
    @Singleton
    fun provideFireStore() = FirebaseStorage.getInstance()

    @Singleton
    @Provides
    fun provideFirebaseStorage() = FirebaseStorage.getInstance()


    @Provides
    @Singleton
    fun provideFirebaseDatabase() = FirebaseDatabase.getInstance()


    @Provides
    @Singleton
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideCurrentUserID() = FirebaseAuth.getInstance().uid
}