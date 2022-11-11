package com.vn.wecare.feature.home.step_count.data.dao

import androidx.room.*
import com.vn.wecare.feature.home.step_count.data.entity.StepsPerDayEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StepsPerDayDao {
    @Query("SELECT * FROM STEPS_PER_DAY WHERE day_id = :dayId")
    fun getDayById(dayId: String): Flow<StepsPerDayEntity>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewDay(stepsPerDayEntity: StepsPerDayEntity)

    @Delete
    suspend fun deleteAllDays()

    @Delete
    suspend fun deleteFurthestDay(stepsPerDayEntity: StepsPerDayEntity)
}