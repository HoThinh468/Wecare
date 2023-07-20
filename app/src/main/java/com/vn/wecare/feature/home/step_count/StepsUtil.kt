package com.vn.wecare.feature.home.step_count

class StepsUtil {
    companion object {

        fun getStrideByUserHeight(height: Int): Float {
            return (height.toFloat() / 100f * 0.414f)
        }

        fun getDistanceBySteps(steps: Int, stride: Float): Float {
            return steps * stride
        }

        fun getAverageMoveTimeByDistance(distance: Float): Float {
            return (distance / 1.34f)
        }

        fun getMoveTimeRequiredBySteps(steps: Int, height: Int): Int {
            val stride = getStrideByUserHeight(height)
            val distance = getDistanceBySteps(steps, stride)
            // Unit return: seconds
            return getAverageMoveTimeByDistance(distance).toInt()
        }

        fun getCaloriesBurnedBySteps(steps: Int, height: Int, weight: Int): Int {
            val stride = getStrideByUserHeight(height)
            val distance = getDistanceBySteps(steps, stride)
            val time = getAverageMoveTimeByDistance(distance) // Average pace
            // 3.5 is the average MET
            return (time * 3.5 * 3.5 * (weight.toFloat() / 12000)).toInt()
        }

        fun getStepsByCaloriesBurned(caloriesBurned: Int, height: Int, weight: Int): Int {
            val stride = getStrideByUserHeight(height) // 0.7
            return ((caloriesBurned * 1.34 * 12000) / (stride * 3.5 * 3.5 * weight)).toInt()
        }
    }
}