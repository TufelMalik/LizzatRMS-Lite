package com.tufelmalik.lizzatresturentlite.classes

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.tufelmalik.lizzatresturentlite.classes.FirebaseModule.provideFirebaseAuth
import com.tufelmalik.lizzatresturentlite.classes.FirebaseModule.provideFirebaseDatabase
import com.tufelmalik.lizzatresturentlite.data.repo.AuthRepository
import com.tufelmalik.lizzatresturentlite.data.repo.FoodRepository
import com.tufelmalik.lizzatresturentlite.data.repo.interfaces.FoodRepositoryInterface
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
    fun provideAuthRepository() = AuthRepository(
        provideFirebaseAuth(),
        provideFirebaseDatabase()
    )

    @Provides
    @Singleton
    fun provideFoodRepository(
        database: FirebaseFirestore,
        storage : FirebaseStorage
    ): FoodRepository{
        return FoodRepository(database,storage)
    }

}