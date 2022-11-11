package com.vn.wecare.feature.home.step_count.data.dao

import androidx.room.*
import com.vn.wecare.feature.home.step_count.data.entity.StepsPerDayWithHours
import com.vn.wecare.feature.home.step_count.data.entity.StepsPerHourEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StepsPerHourDao {
    /**
     * This query will tell Room to query both the [StepsPerDay] and [StepsPerHour] tables and handle
     * the object mapping.
     */
    @Transaction
    @Query("SELECT * FROM steps_per_day WHERE user_id = :userId AND day_id = :dayId")
    fun getStepsPerDayWithHours(userId: String, dayId: String): Flow<List<StepsPerDayWithHours>>

    /**
     * Set the on conflict strategy to replace to make sure old users data won't be leak when system
     * get trouble in deleting it
     */
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewHour(stepsPerHourEntity: StepsPerHourEntity)

    @Delete
    suspend fun deleteAllHours()

    // TODO: Check whether we can delete a stepsPerHour with dayID and hour or not
    @Delete
    suspend fun deleteFurthest24Hours(stepsPerHourEntity: StepsPerHourEntity)
}