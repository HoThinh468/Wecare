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
}