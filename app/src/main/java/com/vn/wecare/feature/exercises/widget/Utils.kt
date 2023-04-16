package com.vn.wecare.feature.exercises.widget

fun slitWeek(list: List<Int>?, week: Int ) : List<Int?>{
    val result = mutableListOf<List<Int?>>()
    if (list != null) {
        var startIndex = 0
        for (i in 0 until 4) {
            val sublist = mutableListOf<Int?>()
            for (j in 0 until 7) {
                val index = startIndex + j
                if (index < list.size) {
                    sublist.add(list[index])
                } else {
                    sublist.add(null)
                }
            }
            startIndex += 7
            result.add(sublist)
        }
    } else {
        for (i in 0 until 4) {
            result.add(List(7) { null })
        }
    }

    return result[week-1]
}

fun checkIsNull(number: Int?): Boolean {
    if (number != null)
        return true
    return false
}