package com.vn.wecare.feature.exercises.done

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vn.wecare.core.model.ExerciseType
import com.vn.wecare.feature.exercises.usecase.Usecases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class DoneViewModel @Inject constructor(
    private val usecases: Usecases
) : ViewModel() {
    private val _feedbackContent = MutableStateFlow("")
    val feedbackContent: StateFlow<String>
        get() = _feedbackContent

    fun setContent(newContent: String) {
        _feedbackContent.value = newContent
    }

    private val _rating = MutableStateFlow(0)
    val rating: StateFlow<Int>
        get() = _rating

    fun setRating(newRating: Int) {
        _rating.value = newRating
    }

    fun addNewReview(
        type: ExerciseType,
        index: Int
    ) = viewModelScope.launch {
        usecases.addNewReview(type, index, feedbackContent.value, rating.value)
    }

    fun updateListDoneFullBody(
        index: Int
    ) = viewModelScope.launch {
        usecases.updateListDoneFullBody(index)
    }
}