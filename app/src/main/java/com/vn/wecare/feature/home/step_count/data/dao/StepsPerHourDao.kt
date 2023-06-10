package com.vn.wecare.feature.home.step_count.data.dao

import androidx.room.*
import com.vn.wecare.feature.home.step_count.data.entity.StepsPerDayEntity
import com.vn.wecare.feature.home.step_count.data.entity.StepsPerHourEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StepsPerHourDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHour(vararg stepsPerHourEntity: StepsPerHourEntity)

    /**
     * This query will tell Room to query both the [StepsPerDayEntity] and [StepsPerHourEntity]
     * tables and handle the object mapping.
     */
    @Query("SELECT * FROM steps_per_hour WHERE dayIncludeId = :dayId")
    fun getStepsPerHourWithDayId(dayId: String): Flow<List<StepsPerHourEntity>>

    @Query("SELECT * FROM steps_per_hour")
    fun getAllStepsPerHour(): Flow<List<StepsPerHourEntity>>

    @Delete
    suspend fun deleteHours(stepsPerHourEntity: List<StepsPerHourEntity>)

    @Query("DELETE FROM steps_per_hour")
    suspend fun deleteAllHours()

    @Query("SELECT * FROM steps_per_hour WHERE hourId = :hourId")
    fun getStepPerHourWithHourId(hourId: String): Flow<StepsPerHourEntity?>
}