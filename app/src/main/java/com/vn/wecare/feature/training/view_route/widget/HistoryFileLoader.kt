package com.vn.wecare.feature.training.view_route.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.mapbox.navigation.core.history.MapboxHistoryReader
import com.mapbox.navigation.core.replay.history.ReplayEventBase
import com.mapbox.navigation.core.replay.history.ReplayHistoryMapper
import com.mapbox.navigation.core.replay.history.ReplaySetNavigationRoute
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileInputStream

class HistoryFileLoader {
    private val replayHistoryMapper = ReplayHistoryMapper.Builder().setRouteMapper {
        ReplaySetNavigationRoute.Builder(eventTimestamp = it.eventTimestamp)
            .route(it.navigationRoute)
            .build()
    }.build()
    private val historyFilesDirectory = HistoryFilesDirectory()

    @SuppressLint("MissingPermission")
    suspend fun loadReplayHistory(
        context: Context
    ): List<ReplayEventBase> = withContext(Dispatchers.IO) {
        loadSelectedHistory() ?: loadDefaultReplayHistory(context)
    }

    private suspend fun loadSelectedHistory(): List<ReplayEventBase>? =
        withContext(Dispatchers.IO) {
            HistoryFilesActivity.selectedHistory?.asSequence()?.mapNotNull { historyEvent ->
                replayHistoryMapper.mapToReplayEvent(historyEvent)
            }?.toList()
        }

    private suspend fun loadDefaultReplayHistory(
        context: Context
    ): List<ReplayEventBase> = withContext(Dispatchers.IO) {
        val fileName = "replay-history-activity.json"
        //val path = "/data/data/com.vn.wecare/files/mbx_nav/history"
        //val inputStream = FileInputStream(File("/data/data/com.vn.wecare/files/mbx_nav/history/replay-history-activity.pbf.gz"))
        val inputStream = context.assets.open(fileName)
        val outputFile = historyFilesDirectory.outputFile(context, fileName)
        outputFile.outputStream().use { fileOut ->
            inputStream.copyTo(fileOut)
        }
        //MapboxHistoryReader("/data/data/com.vn.wecare/files/mbx_nav/history/replay-history-activity.pbf.gz")
        MapboxHistoryReader(outputFile.absolutePath)
            .asSequence()
            .mapNotNull { replayHistoryMapper.mapToReplayEvent(it) }
            .toList()
    }
}
