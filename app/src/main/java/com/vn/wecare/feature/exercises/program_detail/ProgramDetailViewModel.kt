package com.vn.wecare.feature.exercises.program_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vn.wecare.core.data.Response
import com.vn.wecare.core.model.ExerciseType
import com.vn.wecare.core.model.ListReviewsItem
import com.vn.wecare.feature.exercises.usecase.Usecases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProgramDetailViewModel @Inject constructor(
    private val exerciseUsecases: Usecases
) : ViewModel() {

    var reviewListResponse by mutableStateOf<Response<List<ListReviewsItem>>>(Response.Loading)
        private set

    private var _listReviews = MutableStateFlow<List<ListReviewsItem>?>(null)
    val listReviews: StateFlow<List<ListReviewsItem>?>
        get() = _listReviews

    fun getListReview(type: ExerciseType, index: Int) = viewModelScope.launch {
        exerciseUsecases.getReviewList(type, index).collect { response ->
            reviewListResponse = response
            if (reviewListResponse is Response.Success) {
                _listReviews.value =
                    (reviewListResponse as Response.Success<List<ListReviewsItem>>).data
            }
        }
    }

    fun getRating(list: List<ListReviewsItem>): Int {
        var rating = 0
        if (list.isEmpty()) return 0
        for (i in list) {
            rating += i.rate
        }
        return rating / list.size
    }

    fun getReviewCount(list: List<ListReviewsItem>): Int {
        return list.size
    }
}