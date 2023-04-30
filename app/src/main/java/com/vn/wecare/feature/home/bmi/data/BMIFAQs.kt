package com.vn.wecare.feature.home.bmi.data

data class BMIFAQsModel(
    val question: String = "", val answer: String = ""
)

object BMIFAQs {
    val instance = listOf(
        BMIFAQsModel(
            "What is BMI?",
            "The body mass index (BMI) is a measure that uses your height and weight to work out if your weight is healthy."
        ), BMIFAQsModel(
            "How can I calculate my BMI?",
            "You can easily calculate your BMI by dividing your weight by the square of your height where weight in kilogram and height in meter. The unit of BMI is kg/m2"
        ), BMIFAQsModel(
            "Is BMI good to trust?",
            "BMI is an imperfect but important tool to aid in diagnosing obesity and risk for disease, so most doctors use it as one piece of a puzzle to determine the health of an individual. The BMI was developed to identify people with excess body fat, but it does not take into consideration the other components of the body: bone, water, and muscle."
        ), BMIFAQsModel(
            "What is a healthy BMI?",
            "The healthy range for BMI is considered to be between 18 and 24.9. While you may think that the lower your BMI is, the better, a person with a BMI below 18 is actually considered underweight."
        ), BMIFAQsModel(
            "How to have a healthy BMI?",
            "A balanced, calorie-controlled diet is the ticket to a healthy BMI – the safe way. By making simple healthier food swaps, reducing portion sizes and cutting down on high-calorie food and drink, you’re more likely to reach a healthy BMI steadily, and keep the extra weight off."
        )
    )
}