package com.tufelmalik.lizzatresturentlite.data.repo

import android.net.Uri
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.tufelmalik.lizzatresturentlite.classes.MyResult
import com.tufelmalik.lizzatresturentlite.classes.Utilities
import com.tufelmalik.lizzatresturentlite.data.Food
import com.tufelmalik.lizzatresturentlite.data.repo.interfaces.FoodRepositoryInterface
import java.util.UUID

class FoodRepository(
    private val db: FirebaseFirestore,
    private var storage: FirebaseStorage
) : FoodRepositoryInterface {


    override suspend fun saveFoodData(
        category: String,
        food: Food,
        result: (MyResult<Pair<Food, String>>) -> Unit
    ) {
        val uniqKey = UUID.randomUUID().toString()
        val foodImageRef = storage.reference.child(Utilities.FirebaseFireStoreFood.ROOT_DIR)
            .child(category).child(uniqKey)
        foodImageRef.putFile(Uri.parse(food.foodImageUri))
            .addOnSuccessListener {
                foodImageRef.downloadUrl.addOnSuccessListener {
                    val newImageUri =  it.toString()
                    saveFoodInFireStore(uniqKey,category,newImageUri,food){state->
                        when(state){
                            when (state) {
                                is MyResult.Success -> {
                                    result.invoke(MyResult.Success(Pair(food, "Data Inserted Successful..")))
                                }

                                is MyResult.Error -> result.invoke(MyResult.Error(state.errorMsg))

                                else -> MyResult.Loading
                            } ->{}
                            else -> {}
                        }
                    }
                }
                    .addOnFailureListener {
                        result.invoke(
                            MyResult.Error(it.message)
                        )
                    }
            }
            .addOnFailureListener {
                result.invoke(
                    MyResult.Error(it.message)
                )
            }
    }

    override fun saveFoodInFireStore(
        uniqueKey : String,
        category: String,
        newImageUri: String,
        food: Food,
        result: (MyResult<String>) -> Unit
    ) {
        food.foodImageUri = newImageUri
        food.foodId = uniqueKey
        val collectionRef  = db.collection(Utilities.FirebaseFireStoreFood.ROOT_DIR)
            .document(category).collection(uniqueKey)
            .document("FoodData")

        collectionRef.set(food)
            .addOnSuccessListener {
                result(MyResult.Success("Food saved successfully"))
            }
            .addOnFailureListener {
                result(MyResult.Error(it.message))
            }
    }


    override suspend fun getFoodList(category: String, result: (MyResult<List<Food>>) -> Unit) {
        TODO("Not yet implemented")
    }




    override suspend fun updateFoodData(
        foodId: String,
        category: String,
        food: Food,
        result: (MyResult<Pair<Food, String>>) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteFoodData(
        foodId: String,
        category: String,
        result: (MyResult<Pair<Food, String>>) -> Unit
    ) {
        TODO("Not yet implemented")
    }


}