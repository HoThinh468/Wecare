package com.vn.wecare.core.model

data class ListReviewsItem(
    val userName: String = "",
    val userId: String = "",
    val rate: Int = 0,
    val time: Long = 0,
    val likeCount: Int = 0,
    val content: String = "",
    val listLikeAccount: List<String> = listOf("")
)

val listReviewError: List<ListReviewsItem> = listOf(
    ListReviewsItem(
        userName = "",
        rate = 0,
        time = 0,
        likeCount = 0,
        content = ""
    ),
)

data class ListDone(
    val listDone: List<Int>? = listOf()
)