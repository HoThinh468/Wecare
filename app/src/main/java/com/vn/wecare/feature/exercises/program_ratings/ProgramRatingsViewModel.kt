package com.vn.wecare.feature.exercises.program_ratings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.vn.wecare.core.data.Response
import com.vn.wecare.core.model.ExerciseType
import com.vn.wecare.core.model.ListReviewsItem
import com.vn.wecare.feature.exercises.usecase.Usecases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProgramRatingsViewModel @Inject constructor(
    private val exerciseUsecases: Usecases,
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _filterRating = MutableStateFlow<Int>(0)
    val filterRating: StateFlow<Int>
        get() = _filterRating

    fun setFilterRating(newValue: Int) {
        _filterRating.value = newValue
    }

    private val _listReview = MutableStateFlow<List<ListReviewsItem>?>(null)
    val listReview: StateFlow<List<ListReviewsItem>?>
        get() = _listReview

    private val _listAllReview = MutableStateFlow<List<ListReviewsItem>?>(null)
    val listAllReview: StateFlow<List<ListReviewsItem>?>
        get() = listAllReview

    private val _likeReviewResponse = MutableStateFlow<Response<Boolean>>(Response.Loading)
    val likeReviewResponse: StateFlow<Response<Boolean>>
        get() = _likeReviewResponse

    var likeCountReviewResponse by mutableStateOf<Response<Int>>(Response.Loading)
        private set

    fun setListReview(list: List<ListReviewsItem>) {
        _listReview.value = list
        _listAllReview.value = list
    }

    fun filterListReview(rating: Int) {
        _listReview.value = _listAllReview.value
        if (rating != 0) {
            _listReview.value?.let { list ->
                val filteredList = list.filter { it.rate == rating }
                _listReview.value = filteredList
            }
        }
    }

    fun getLikeCountResponse(
        type: ExerciseType,
        index: Int,
        indexReview: Int
    ) {
        viewModelScope.launch {
            exerciseUsecases.getReviewLikeCount(type, index, indexReview)
                .collect { response: Response<Int> ->
                    likeCountReviewResponse = response
                }
        }
    }

    fun checkIsLiked(
        indexReview: Int
    ): Boolean {
        _listReview.value?.let {
            auth.currentUser?.uid?.let { id ->
                if (it[indexReview].listLikeAccount.contains(id)) {
                    return true
                }
            }
        }
        return false
    }

    fun likeReview(
        type: ExerciseType,
        index: Int,
        indexReview: Int,
        newLikeCount: Int,
        onRefreshList: () -> Unit
    ) = viewModelScope.launch {
        if(!checkIsLiked(indexReview)) {
            _likeReviewResponse.value =
                exerciseUsecases.likeReview(type, index, indexReview, newLikeCount)
            exerciseUsecases.updateListLikeAccount(type, index, indexReview)
            getLikeCountResponse(type, index, indexReview)
            onRefreshList()
        }
    }
}

data class ProgramRatingUI(
    val title: String,
    val rating: Int,
    val ratedNumber: Int,
    val listReview: List<ListReviewsItem>,
    val type: ExerciseType,
    val exerciseIndex: Int
) : java.io.Serializable