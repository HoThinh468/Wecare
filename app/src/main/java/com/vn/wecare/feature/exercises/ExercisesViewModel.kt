package com.vn.wecare.feature.exercises

import androidx.lifecycle.ViewModel
import com.vn.wecare.R
import com.vn.wecare.core.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.Serializable
import javax.inject.Inject

@HiltViewModel
class ExercisesViewModel @Inject constructor(

) : ViewModel() {

    private var _currentWorkoutIndex = MutableStateFlow<Int>(0)
    val currentWorkoutIndex: StateFlow<Int>
        get() = _currentWorkoutIndex

    fun increaseWorkoutIndex() {
        _currentWorkoutIndex.value++
    }

    fun decreaseWorkoutIndex() {
        _currentWorkoutIndex.value--
    }

    private var _currentWorkoutList = MutableStateFlow<List<ProgramDetailItem>>(errorList)
    val currentWorkoutList: StateFlow<List<ProgramDetailItem>>
        get() = _currentWorkoutList

    fun setCurrentWorkoutList(list: List<ProgramDetailItem>) {
        _currentWorkoutList.value = list
    }

    fun getCurrentWorkout(): ProgramDetailItem {
        return _currentWorkoutList.value[_currentWorkoutIndex.value]
    }

    val enduranceList = ExerciseListScreenUI(
        image = R.drawable.endurance_img,
        title = "Endurance exercises",
        trackAmount = 9,
        listExercise = listEndurance
    )
    val strengthList = ExerciseListScreenUI(
        image = R.drawable.strength_img,
        title = "Strength exercises",
        trackAmount = 6,
        listExercise = listStrength
    )
    val balanceList = ExerciseListScreenUI(
        image = R.drawable.balance_img,
        title = "Balance exercises",
        trackAmount = 5,
        listExercise = listStrength
    )
    val flexibilityList = ExerciseListScreenUI(
        image = R.drawable.flexibility_img,
        title = "Flexibility exercises",
        trackAmount = 7,
        listExercise = listStrength
    )
}

data class ExerciseListScreenUI(
    val image: Int,
    val title: String,
    val trackAmount: Int,
    val listExercise: List<ListExerciseItem>
) : Serializable