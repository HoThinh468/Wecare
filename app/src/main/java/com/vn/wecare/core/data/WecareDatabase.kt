package com.vn.wecare.core.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.vn.wecare.feature.home.step_count.data.entity.StepsPerDay
import com.vn.wecare.feature.home.step_count.data.dao.StepsPerDayDao
import com.vn.wecare.feature.home.step_count.data.entity.StepsPerHour
import com.vn.wecare.feature.home.step_count.data.dao.StepsPerHourDao

@Database(
    entities = [
        StepsPerDay::class,
        StepsPerHour::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class WecareDatabase : RoomDatabase() {

    abstract fun StepsPerDayDao(): StepsPerDayDao
    abstract fun StepsPerHourDao(): StepsPerHourDao

    companion object {
        // For Singleton instantiation
        @Volatile
        private var INSTANCE: WecareDatabase? = null

        fun getDatabase(context: Context): WecareDatabase {
            return INSTANCE ?: synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        WecareDatabase::class.java,
                        "wecare_db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                instance
            }
        }
    }
}