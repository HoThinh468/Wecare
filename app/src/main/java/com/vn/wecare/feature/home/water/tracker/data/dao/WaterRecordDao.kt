package com.vn.wecare.feature.home.water.tracker.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.vn.wecare.feature.home.water.tracker.data.WaterRecordEntity

@Dao
interface WaterRecordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecord(vararg record: WaterRecordEntity)

    @Delete
    suspend fun deleteRecord(record: WaterRecordEntity)

    @Query("SELECT * FROM water_record")
    fun getAllRecords(): List<WaterRecordEntity>?

    @Query("SELECT * FROM water_record WHERE dayId =:dayId")
    fun getRecordWithDayId(dayId: String): List<WaterRecordEntity>?

    @Update
    fun updateRecord(vararg record: WaterRecordEntity)

    @Query("DELETE FROM water_record")
    suspend fun deleteAllRecords()
}