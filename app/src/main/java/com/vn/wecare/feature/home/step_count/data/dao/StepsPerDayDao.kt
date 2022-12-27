package com.vn.wecare.feature.home.step_count.data.dao

import androidx.room.*
import com.vn.wecare.feature.home.step_count.data.entity.StepsPerDayEntity

@Dao
interface StepsPerDayDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDay(vararg stepsPerDayEntity: StepsPerDayEntity)

    @Delete
    suspend fun deleteDay(vararg stepsPerDayEntity: StepsPerDayEntity): Int

    @Query("DELETE FROM steps_per_day")
    suspend fun deleteAllDays()
}