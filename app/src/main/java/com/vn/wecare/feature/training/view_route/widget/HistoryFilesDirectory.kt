package com.vn.wecare.feature.training.view_route.widget

import android.content.Context
import android.util.Log
import com.mapbox.navigation.core.history.MapboxHistoryReader
import java.io.File

/**
 * Helper class that designates a directory for replay. The files in this
 * directory are then used with the [MapboxHistoryReader].
 */
class HistoryFilesDirectory {
    /**
     * The directory where the replay files are stored.
     */
    fun replayDirectory(context: Context) : File {
        Log.d("replay Direction:", DIRECTORY_NAME)
        return File(context.filesDir, DIRECTORY_NAME).also { it.mkdirs() }
    }
    /**
     * Returns a list of history files from the [replayDirectory].
     */
    fun replayFiles(context: Context) =
        replayDirectory(context).walk().filterNot { it.isDirectory }.toList()

    /**
     * Returns a file in the [replayDirectory] where a history file can be written.
     */
    fun outputFile(context: Context, path: String) =
        File(replayDirectory(context), path)

    companion object {
        private const val DIRECTORY_NAME = "/data/data/com.vn.wecare/files/mbx_nav/history"
    }
}
