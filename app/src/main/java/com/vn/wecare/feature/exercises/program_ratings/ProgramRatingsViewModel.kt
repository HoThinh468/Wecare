package com.vn.wecare.feature.exercises.program_ratings

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ProgramRatingsViewModel @Inject constructor(

): ViewModel() {

    private val _filterStar = MutableStateFlow<Int>(0)
    val filterStar: StateFlow<Int>
        get() = _filterStar

    fun setFilterStar(newValue: Int) {
        _filterStar.value = newValue
    }

}