package com.vn.wecare.feature.home.water.tracker.data.datasource

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.vn.wecare.core.data.Response
import com.vn.wecare.core.di.IoDispatcher
import com.vn.wecare.feature.home.water.tracker.ui.WaterFragment
import com.vn.wecare.feature.home.water.tracker.data.model.WaterRecordEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Calendar
import javax.inject.Inject

private const val WATER_COLLECTION_PATH = "water"

class WaterRecordRemoteDataSource @Inject constructor(
    private val db: FirebaseFirestore, @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : WaterRecordDataSource {

    override suspend fun insert(recordEntity: WaterRecordEntity) {
        db.collection(WATER_COLLECTION_PATH).document(recordEntity.userId)
            .collection(recordEntity.dateTime.get(Calendar.YEAR).toString())
            .document(recordEntity.dateTime.get(Calendar.MONTH).toString())
            .collection(recordEntity.dateTime.get(Calendar.DAY_OF_MONTH).toString())
            .document(recordEntity.recordId.toString()).set(recordEntity).addOnSuccessListener {
                Log.d(WaterFragment.waterTrackerTag, "Save water record to remote success!")
            }.addOnSuccessListener {
                Log.d(WaterFragment.waterTrackerTag, "Save water record to remote fail!")
            }
    }

    override suspend fun delete(recordEntity: WaterRecordEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll() {
        TODO("Not yet implemented")
    }

    override fun getRecordWithId(recordId: Long): Flow<Response<WaterRecordEntity?>> = flow {
    }

    override fun getRecordsWithDayId(dayId: String): Flow<Response<List<WaterRecordEntity>?>> {
        TODO("Not yet implemented")
    }
}