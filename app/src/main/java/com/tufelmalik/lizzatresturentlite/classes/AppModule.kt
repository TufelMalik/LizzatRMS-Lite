package com.tufelmalik.lizzatresturentlite.classes

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.tufelmalik.lizzatresturentlite.data.repo.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.internal.managers.ApplicationComponentManager
import dagger.hilt.components.SingletonComponent
import org.checkerframework.checker.initialization.qual.Initialized
import javax.inject.Singleton
import kotlin.jvm.internal.Ref.DoubleRef

@Module
@InstallIn(SingletonComponent::class)

object AppModule {

    @Provides
    @Singleton
    fun provideFireStoreInstance() = FirebaseStorage.getInstance()

    @Singleton
    @Provides
    fun provideFirebaseStorageInstance() = FirebaseStorage.getInstance().getReference(Utilities.FirestoreData.USER)



    @Provides
    @Singleton
    fun provideFirebaseDatabaseInstance() = FirebaseDatabase.getInstance()


    @Provides
    @Singleton
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideCurrentUserID() = FirebaseAuth.getInstance().uid

    @Provides
    @Singleton
    fun provideAuthRepository() = AuthRepository(
        provideFirebaseAuth(),
        provideFirebaseDatabaseInstance()
    )

}