package com.example.task

enum class PercentageColor {
    RED, YELLOW, GREEN;

    companion object {
        fun fromPercentage(value: Double?): PercentageColor {
            return when {
                value == null || value in 0.00..0.20 -> RED
                value in 0.20..0.40 -> YELLOW
                else -> GREEN
            }
        }
    }
}
