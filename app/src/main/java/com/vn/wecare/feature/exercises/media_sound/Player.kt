package com.vn.wecare.feature.exercises.media_sound

import android.content.Context
import android.media.MediaPlayer
import com.vn.wecare.R

class Player(private val context: Context) {

    var mMediaPlayer: MediaPlayer? = null

    // 1. Plays the water sound
    fun playSound( sound: Int = R.raw.countdown) {
        if (mMediaPlayer == null) {
            mMediaPlayer = MediaPlayer.create(context, sound)
            mMediaPlayer!!.isLooping = true
            mMediaPlayer!!.start()
        } else mMediaPlayer!!.start()
    }

    // 2. Pause playback
    fun pauseSound() {
        if (mMediaPlayer?.isPlaying == true) mMediaPlayer?.pause()
    }

    // 3. Stops playback
    fun stopSound() {
        if (mMediaPlayer != null) {
            mMediaPlayer!!.stop()
            mMediaPlayer!!.release()
            mMediaPlayer = null
        }
    }

//    // 4. Destroys the MediaPlayer instance when the app is closed
//    override fun onStop() {
//        super.onStop()
//        if (mMediaPlayer != null) {
//            mMediaPlayer!!.release()
//            mMediaPlayer = null
//        }
//    }
}