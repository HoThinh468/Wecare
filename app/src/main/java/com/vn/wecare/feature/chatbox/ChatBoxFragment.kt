package com.vn.wecare.feature.chatbox

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.vn.wecare.R
import com.vn.wecare.databinding.FragmentChatBoxBinding
import com.vn.wecare.databinding.FragmentExerciseListBinding
import com.vn.wecare.feature.exercises.exercise_list.ExerciseListScreen
import com.vn.wecare.ui.theme.WecareTheme

class ChatBoxFragment : Fragment() {

    private var _binding: FragmentChatBoxBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val dialogflowClient = DialogFlowClient(context = requireContext())
        val text = "I'm looking for an exercise"
        val response = dialogflowClient.sendRequest(text)
        val message = response.queryResult.fulfillmentText
        Log.e("trung log message", message)



        _binding = FragmentChatBoxBinding.inflate(inflater, container, false)

        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                ChatBoxScreen()
            }
        }

        return binding.root
    }
}