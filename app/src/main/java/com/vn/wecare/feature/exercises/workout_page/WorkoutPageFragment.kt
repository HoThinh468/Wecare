package com.vn.wecare.feature.exercises.workout_page

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.vn.wecare.R
import com.vn.wecare.databinding.FragmentExercisesBinding
import com.vn.wecare.databinding.FragmentWorkoutPageBinding
import com.vn.wecare.feature.exercises.ExercisesViewModel
import com.vn.wecare.ui.theme.WecareTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class WorkoutPageFragment : Fragment(), TextToSpeech.OnInitListener {

    private var _binding: FragmentWorkoutPageBinding? = null
    private val binding get() = _binding!!

    private var tts: TextToSpeech? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tts = TextToSpeech(requireContext(), this)
    }

    val viewModel: ExercisesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWorkoutPageBinding.inflate(inflater, container, false)

        requireActivity().onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                }
            })

        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                WecareTheme {
                    WorkoutPageScreen(
                        onQuit = {
                            findNavController().navigate(R.id.action_workoutPageFragment_to_exercisesFragment)
                        },
                        title = viewModel.getCurrentWorkout().title,
                        duration = viewModel.getCurrentWorkout().duration,
                        exercise = viewModel.getCurrentWorkout().exercise,
                        onNavigateToRest = {
                            if (viewModel.currentWorkoutIndex.value == viewModel.currentWorkoutList.value.size - 1) {
                                viewModel.onEndWorkout()
                                findNavController().navigate(R.id.action_workoutPageFragment_to_doneFragment2)
                            } else {
                                viewModel.increaseWorkoutIndex()
                                findNavController().navigate(R.id.action_workoutPageFragment_to_workoutRestFragment2)
                            }
                        },
                        onNavigateToPreviousRest = {
                            viewModel.decreaseWorkoutIndex()
                            findNavController().navigate(R.id.action_workoutPageFragment_to_workoutRestFragment2)
                        },
                        context = requireContext()
                    )
                }
            }
        }
        return binding.root
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts!!.setLanguage(Locale.US)
            tts!!.setSpeechRate(0.7f)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The Language not supported!")
            } else {
                speakOut(
                    "Start !! ${viewModel.getCurrentWorkout().duration} seconds ${viewModel.getCurrentWorkout().title}"
                )
            }
        }
    }

    private fun speakOut(text: String) {
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    override fun onDestroy() {
        // Shutdown TTS when
        // activity is destroyed
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }
}

fun handleOnBackPress() {

}