package com.vn.wecare.feature.exercises.media_sound
//
//import android.content.Context
//import android.speech.tts.TextToSpeech
//import android.speech.tts.UtteranceProgressListener
//import java.util.*
//
//class AudioSound(context: Context) {
//    val stringInput = "Hello, world!" // Replace with your desired string input
//
//    val tts = TextToSpeech(context) { status ->
//        if (status == TextToSpeech.SUCCESS) {
//            val result = tts.setLanguage(Locale.US) // Set the desired language
//            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
//                // Handle errors
//            } else {
//                val map = HashMap<String, String>()
//                map[TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID] = "utteranceId"
//                tts.synthesizeToFile(
//                    stringInput,
//                    map,
//                    "/sdcard/myaudiofile.wav"
//                ) // Generate audio file
//            }
//        } else {
//            // Handle errors
//        }
//    }
//
//    tts.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
//        override fun onStart(utteranceId: String?) {}
//
//        override fun onDone(utteranceId: String?) {
//            val mediaPlayer = MediaPlayer()
//            mediaPlayer.setDataSource("/sdcard/myaudiofile.wav") // Set audio file path
//            mediaPlayer.prepare()
//            mediaPlayer.start() // Play audio file
//        }
//
//        override fun onError(utteranceId: String?) {}
//    })
//
//}