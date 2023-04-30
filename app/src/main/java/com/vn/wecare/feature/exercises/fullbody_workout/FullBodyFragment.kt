package com.vn.wecare.feature.exercises.fullbody_workout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.vn.wecare.R
import com.vn.wecare.core.model.listFullBody
import com.vn.wecare.databinding.FragmentExerciseListBinding
import com.vn.wecare.databinding.FragmentFullBodyBinding
import com.vn.wecare.feature.exercises.ExercisesViewModel
import com.vn.wecare.ui.theme.WecareTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FullBodyFragment : Fragment() {

    private var _binding: FragmentFullBodyBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    val viewModel: ExercisesViewModel by activityViewModels()
    lateinit var bundle: Bundle

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFullBodyBinding.inflate(inflater, container, false)

        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                WecareTheme {
                    FullBodyWorkout(
                        onNavigateToDetail = {
                            bundle = bundleOf("listDetail" to Pair(listFullBody[it - 1], it - 1))

                            findNavController().navigate(
                                R.id.action_fullBodyFragment_to_programDetailFragment,
                                bundle
                            )
                        },
                        onNavigateBack = {
                            findNavController().popBackStack()
                        }
                    )
                }
            }
        }
        return binding.root
    }
}