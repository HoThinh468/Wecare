package com.vn.wecare.feature.exercises.done

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.vn.wecare.R
import com.vn.wecare.databinding.FragmentDone2Binding
import com.vn.wecare.databinding.FragmentDoneBinding
import com.vn.wecare.databinding.FragmentExerciseListBinding
import com.vn.wecare.feature.exercises.ExercisesViewModel
import com.vn.wecare.feature.exercises.exercise_list.ExerciseListScreen
import com.vn.wecare.ui.theme.WecareTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DoneFragment : Fragment() {

    private var _binding: FragmentDone2Binding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    val viewModel: ExercisesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDone2Binding.inflate(inflater, container, false)

        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                WecareTheme {
                    DoneScreen(
                        onDone = {
                            findNavController().navigate(R.id.action_doneFragment2_to_exercisesFragment)
                        },
                        exercisesViewModel = viewModel
                    )
                }
            }
        }

        return binding.root
    }
}