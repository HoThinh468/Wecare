package com.vn.wecare.feature.home.water.tracker.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.vn.wecare.core.data.Response
import kotlinx.coroutines.flow.Flow

@Dao
interface WaterRecordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecord(vararg record: WaterRecordEntity)

    @Delete
    suspend fun deleteRecord(record: WaterRecordEntity)

    @Query("SELECT * FROM water_record")
    fun getAllRecords(): List<WaterRecordEntity>?

    @Update
    fun updateRecord(vararg record: WaterRecordEntity)

    @Query("DELETE FROM water_record")
    suspend fun deleteAllRecords()
}