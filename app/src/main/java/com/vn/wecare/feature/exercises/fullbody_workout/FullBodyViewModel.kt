package com.vn.wecare.feature.exercises.fullbody_workout

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vn.wecare.core.data.Response
import com.vn.wecare.core.model.ListDone
import com.vn.wecare.feature.exercises.usecase.Usecases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class FullBodyViewModel @Inject constructor(
    private val exerciseUsecases: Usecases
): ViewModel() {

    init {
        getListDone()
        Log.d("trung log", "b")
    }

    var getListDoneResponse by mutableStateOf<Response<ListDone?>?>(Response.Loading)
        private set

    private fun getListDone() = viewModelScope.launch {
        Log.d("trung log", "a")
        exerciseUsecases.getListDoneFullBody().collect { response ->
            getListDoneResponse = response
        }
    }
}