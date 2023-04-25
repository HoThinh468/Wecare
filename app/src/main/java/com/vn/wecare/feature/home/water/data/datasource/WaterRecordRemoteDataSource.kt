package com.vn.wecare.feature.home.water.data.datasource

import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.vn.wecare.core.data.Response
import com.vn.wecare.core.di.IoDispatcher
import com.vn.wecare.feature.authentication.service.AccountService
import com.vn.wecare.feature.home.water.data.model.WaterRecord
import com.vn.wecare.feature.home.water.data.model.WaterRecordEntity
import com.vn.wecare.feature.home.water.data.model.toEntity
import com.vn.wecare.feature.home.water.data.model.toModel
import com.vn.wecare.feature.home.water.report.WaterReportFragment
import com.vn.wecare.feature.home.water.tracker.ui.WaterFragment
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import java.util.Calendar
import javax.inject.Inject

private const val WATER_COLLECTION_PATH = "water"

class WaterRecordRemoteDataSource @Inject constructor(
    private val db: FirebaseFirestore,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val accountService: AccountService
) : WaterRecordDataSource {

    override suspend fun insert(recordEntity: WaterRecordEntity) {
        getWaterDbDocument(recordEntity).document(recordEntity.recordId).set(recordEntity.toModel())
            .addOnSuccessListener {
                Log.d(
                    WaterFragment.waterTrackerTag,
                    "Save water record id ${recordEntity.recordId} to remote success!"
                )
            }.addOnFailureListener {
                Log.d(
                    WaterFragment.waterTrackerTag,
                    "Save water record id ${recordEntity.recordId} to remote fail with exception ${it.message}"
                )
            }
    }

    override suspend fun delete(recordEntity: WaterRecordEntity) {
        getWaterDbDocument(recordEntity).document(recordEntity.recordId).delete()
            .addOnSuccessListener {
                Log.d(
                    WaterFragment.waterTrackerTag,
                    "Delete water record id ${recordEntity.recordId} in remote success!"
                )
            }.addOnFailureListener {
                Log.d(
                    WaterFragment.waterTrackerTag,
                    "Delete water record id ${recordEntity.recordId} in remote fail with exception: ${it.message}"
                )
            }
    }

    override suspend fun deleteAll() {
        TODO("Not yet implemented")
    }

    override fun getRecordWithId(dateTime: Calendar): Flow<Response<WaterRecordEntity?>> {
        TODO("Not yet implemented")
    }

    override fun getRecordsWithDayId(
        day: Int, month: Int, year: Int
    ): Flow<Response<List<WaterRecordEntity>?>> = flow {
        val recordList = arrayListOf<WaterRecordEntity>()

        try {
            val result = db.collection(WATER_COLLECTION_PATH).document(accountService.currentUserId)
                .collection(year.toString()).document(month.toString()).collection(day.toString())
                .get().await()
            Log.d(
                WaterReportFragment.waterReportTag,
                "Response from firebase with day $day $month $year: ${result.documents[0].data}"
            )
            if (result != null) {
                for (i in result) {
                    recordList.add(i.toObject(WaterRecord::class.java).toEntity())
                }
                emit(Response.Success(recordList))
            } else {
                emit(Response.Error(null))
            }
        } catch (e: Exception) {
            emit(Response.Error(e))
        }
    }.flowOn(ioDispatcher)

    override suspend fun updateRecordAmountWithId(amount: Int, record: WaterRecordEntity) {
        getWaterDbDocument(record).document(record.recordId).update("amount", amount)
            .addOnFailureListener {
                Log.d(
                    WaterFragment.waterTrackerTag,
                    "Update amount of water record id ${record.recordId} in remote success!"
                )
            }.addOnFailureListener {
                Log.d(
                    WaterFragment.waterTrackerTag,
                    "Update amount of water record id ${record.recordId} in remote fail!"
                )
            }
    }

    private fun getWaterDbDocument(record: WaterRecordEntity): CollectionReference {
        return db.collection(WATER_COLLECTION_PATH).document(record.userId)
            .collection(record.dateTime.get(Calendar.YEAR).toString())
            .document(record.dateTime.get(Calendar.MONTH).toString())
            .collection(record.dateTime.get(Calendar.DAY_OF_MONTH).toString())
    }
}