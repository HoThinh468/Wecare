package com.vn.wecare.feature.home.step_count.data.dao

import androidx.room.*
import com.vn.wecare.feature.home.step_count.data.entity.StepsPerDayEntity
import com.vn.wecare.feature.home.step_count.data.entity.StepsPerDayWithHours
import com.vn.wecare.feature.home.step_count.data.entity.StepsPerHourEntity
import dagger.Provides
import kotlinx.coroutines.flow.Flow

@Dao
interface StepsPerHourDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHour(vararg stepsPerHourEntity: StepsPerHourEntity)

    /**
     * This query will tell Room to query both the [StepsPerDayEntity] and [StepsPerHourEntity]
     * tables and handle the object mapping.
     */
    @Transaction
    @Query("SELECT * FROM steps_per_day")
    fun getStepsPerDayWithHours(): Flow<List<StepsPerDayWithHours>>

    @Delete
    suspend fun deleteHours(stepsPerHourEntity: List<StepsPerHourEntity>)

    @Query("DELETE FROM steps_per_hour")
    suspend fun deleteAllHours()
}