package com.vn.wecare.feature.account.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.account.data.model.WecareUser
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewUser(vararg wecareUser: WecareUser)

    @Delete
    suspend fun deleteUser(vararg wecareUser: WecareUser): Int

    @Query("SELECT * FROM wecare_user WHERE userId = :userId")
    fun getUserWithId(vararg userId: String): WecareUser?

    @Query("UPDATE wecare_user SET gender=:gender WHERE userId=:userId")
    fun updateGenderWithUserId(userId: String, gender: Boolean)

    @Query("UPDATE wecare_user SET age=:age WHERE userId=:userId")
    fun updateAgeWithUserId(userId: String, age: Int)

    @Query("UPDATE wecare_user SET height=:height WHERE userId=:userId")
    fun updateHeightWithUserId(userId: String, height: Int)

    @Query("UPDATE wecare_user SET weight=:weight WHERE userId=:userId")
    fun updateWeightWithUserId(userId: String, weight: Int)

    @Query("UPDATE wecare_user SET goal=:goal WHERE userId=:userId")
    fun updateGoalWithUserId(userId: String, goal: String)
}