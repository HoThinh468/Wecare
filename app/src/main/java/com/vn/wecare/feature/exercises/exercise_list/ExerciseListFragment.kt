package com.vn.wecare.feature.exercises.exercise_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.findNavController
import com.vn.wecare.R
import com.vn.wecare.databinding.FragmentExerciseListBinding
import com.vn.wecare.feature.exercises.ExerciseListScreenUI
import com.vn.wecare.ui.theme.WecareTheme

class ExerciseListFragment : Fragment() {

    private var _binding: FragmentExerciseListBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExerciseListBinding.inflate(inflater, container, false)

        val uiState: ExerciseListScreenUI = arguments?.getSerializable("exerciseList") as ExerciseListScreenUI
        lateinit var bundle: Bundle

        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                WecareTheme {
                    ExerciseListScreen(
                        onNavigationBack = { findNavController().popBackStack() },
                        onNavigationProgramDetail = {
                            bundle = bundleOf("listDetail" to uiState.listExercise[it])

                            findNavController().navigate(
                                R.id.action_exerciseListFragment_to_programDetailFragment,
                                bundle
                            )
                        },
                        uiState = uiState
                    )
                }
            }
        }

        return binding.root
    }
}