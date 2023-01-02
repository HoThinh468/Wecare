package com.vn.wecare.feature.home.step_count.data.dao

import androidx.room.*
import com.vn.wecare.feature.home.step_count.data.entity.StepsPerDayEntity
import com.vn.wecare.feature.home.step_count.data.model.StepsPerDay
import kotlinx.coroutines.flow.Flow

@Dao
interface StepsPerDayDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDay(vararg stepsPerDayEntity: StepsPerDayEntity)

    @Delete
    suspend fun deleteDay(vararg stepsPerDayEntity: StepsPerDayEntity): Int

    @Query("DELETE FROM steps_per_day")
    suspend fun deleteAllDays()

    @Query("SELECT * FROM steps_per_day where dayId = :dayId")
    fun getStepsPerDay(vararg dayId: String): Flow<StepsPerDayEntity?>
}