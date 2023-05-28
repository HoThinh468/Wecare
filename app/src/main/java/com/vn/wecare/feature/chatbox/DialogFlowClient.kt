package com.vn.wecare.feature.chatbox

import ai.api.AIConfiguration
import android.content.Context
import com.google.auth.oauth2.GoogleCredentials
import com.vn.wecare.R
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.util.UUID

class DialogFlowClient(private val context: Context) {

    val config = AIConfiguration("c7484419ad8d222f3b03840b6f465c9fcf034108",
        AIConfiguration.SupportedLanguages.English)
    val aiService = AIService.getService(applicationContext, config)

}
